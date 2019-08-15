package com.iazarevsergey.lessons.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.iazarevsergey.lessons.R
import com.iazarevsergey.lessons.domain.model.Search
import com.iazarevsergey.lessons.domain.model.Weather
import com.iazarevsergey.lessons.factory.ViewModelFactory
import com.iazarevsergey.lessons.ui.adapter.CustomSuggestionsAdapter
import com.iazarevsergey.lessons.ui.adapter.WeatherAdapter
import com.iazarevsergey.lessons.ui.base.BaseFragment
import com.iazarevsergey.lessons.ui.listener.SearchActionListener
import com.iazarevsergey.lessons.ui.listener.SearchTextWatcher
import com.iazarevsergey.lessons.viewmodel.ListWeathersViewModel
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_list_weathers.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ListWeathersFragment : BaseFragment(), WeatherAdapter.OnWeathersItemClick,
    CustomSuggestionsAdapter.OnItemViewClickListener {

    //TODO error handling livedata
    //TODO bundle при переходе в detail+в repository
    //TODO dagger ActivityScope

    private lateinit var listWeathersViewModel: ListWeathersViewModel
    private val compositeDisposable = CompositeDisposable()
    private val adapter = WeatherAdapter(this)
    private var isFirstSession = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            isFirstSession = savedInstanceState.getBoolean("isFirstSession")
        }
        listWeathersViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListWeathersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_weathers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBar.setupWithNavController(findNavController())

        initSearchBar()
        initRecyclerView()
        initRefreshListener()
        initObservers()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isFirstSession", isFirstSession)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        arguments?.clear()
    }


    // --------------------------------------------------------------------------------------------------

    private fun initRefreshListener() {
        refreshList.setOnRefreshListener {
            listWeathersViewModel.updateAllWeather()
        }
    }

    private fun initObservers() {
        listWeathersViewModel.getWeathers().observe(this, Observer {
            adapter.setNewItems(it)
            if (it.isEmpty()) {
                empty_list_text.visibility = View.VISIBLE
                refreshList.visibility = View.GONE
            } else {
                if (isFirstSession) {
                    listWeathersViewModel.updateAllWeather()
                    isFirstSession = false
                }
                empty_list_text.visibility = View.GONE
                refreshList.visibility = View.VISIBLE
            }
        })

        listWeathersViewModel.getInfo().observe(this, Observer {
            if (it != null)
                showToastInfo(it)
        })

        listWeathersViewModel.getSearches().observe(this, Observer {
            updateSuggestions(it)
        })

        listWeathersViewModel.getIsRefreshing().observe(this, Observer {
            refreshList.isRefreshing = it
        })

        listWeathersViewModel.getNetworkStatusChanged().observe(this, Observer {
            when (it) {
                true -> showSnackInfo(getString(R.string.internet_on), getColor(context!!, R.color.greenPrimary))
                false -> showSnackInfo(getString(R.string.internet_off), getColor(context!!, R.color.redPrimary))
            }

        })

    }

    private fun showSnackInfo(message: String, color: Int) {
        val snack = Snackbar.make(searchBar, message, Snackbar.LENGTH_LONG)
        snack.view.setBackgroundColor(color)
        snack.show()
    }

    private fun updateSuggestions(items: List<Search>) {
        if (searchBar.isSearchEnabled) {
            searchBar.updateLastSuggestions(items)
        } else {
            searchBar.lastSuggestions = items
        }
    }

    private fun initSearchBar() {
        val inflater = LayoutInflater.from(this.context)
        val customSuggestionsAdapter = CustomSuggestionsAdapter(inflater, this)
        searchBar.setCustomSuggestionAdapter(customSuggestionsAdapter)
        initSearchBarListeners()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter

        initSwipeToDelete()
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listWeathersViewModel.deleteWeather(adapter.items[viewHolder.adapterPosition])
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun onItemClick(item: Weather) {
        val coordinates = listWeathersViewModel.getWeatherCoordinates(item)
        if (coordinates != null) {
            Navigation.findNavController(searchBar)
                .navigate(
                    R.id.action_listWeathersFragment_to_detailWeatherFragment,
                    bundleOf("selectedLocation" to coordinates)
                )
        }
    }

    override fun OnItemClickListener(position: Int) {
        val temp = searchBar.lastSuggestions[position] as Search
        listWeathersViewModel.addWeather(temp.coordinates)
        searchBar.searchEditText.onEditorAction(EditorInfo.IME_ACTION_SEARCH)
    }


    private fun showToastInfo(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun initSearchBarListeners() {

        compositeDisposable.add(Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchBar.addTextChangeListener(object : SearchTextWatcher {

                override fun onTextChanged(string: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    subscriber.onNext(string.toString())
                }
            })
        })
            .map { text -> text.toLowerCase().trim() }
            .debounce(250, TimeUnit.MILLISECONDS)
            .distinct()
            .filter { text -> text.isNotBlank() && text.length > 2 }
            .subscribe { text -> listWeathersViewModel.onTextChanged(text) })

        searchBar.setOnSearchActionListener(object : SearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {
                when (enabled) {
                    true -> {
                        recyclerView.visibility = View.GONE

                    }
                    false -> {
                        recyclerView.visibility = View.VISIBLE

                    }
                }
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                searchBar.disableSearch()
            }
        })
    }

}
