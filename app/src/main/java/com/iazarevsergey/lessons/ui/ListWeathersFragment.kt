package com.iazarevsergey.lessons.ui


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.iazarevsergey.lessons.R
import com.iazarevsergey.lessons.factory.ViewModelFactory
import com.iazarevsergey.lessons.ui.adapter.WeatherAdapter
import com.iazarevsergey.lessons.viewmodel.ListWeathersViewModel
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.fragment_item.view.*
import kotlinx.android.synthetic.main.fragment_list_weathers.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ListWeathersFragment : Fragment(), WeatherAdapter.OnItemListener {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    private lateinit var listWeathersViewModel: ListWeathersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
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
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = WeatherAdapter(this)
        recyclerView.adapter = adapter

        listWeathersViewModel.getWeathers().observe(this, Observer {
            if (it != null)
                adapter.setNewItems(it)
        })

        listWeathersViewModel.getOnError().observe(this, Observer {
            showError(it)
        })

        initSearchBarListeners()

        listWeathersViewModel.getSearches().observe(this, Observer {
            if (searchBar.isSearchEnabled) {
                searchBar.updateLastSuggestions(it)
            } else {
                searchBar.lastSuggestions = it
            }
        })

        listWeathersViewModel.getOnError().observe(this, Observer {
            showError(it)
        })
    }

    override fun onItemClick(position: Int) {
        val weather = listWeathersViewModel.getWeatherByPosition(position)
        val selectedLocation = weather.location_name+","+weather.location_region+","+weather.location_country
        Navigation.findNavController(searchBar).navigate(R.id.action_listWeathersFragment_to_detailWeatherFragment, bundleOf("selectedLocation" to selectedLocation))
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        arguments?.clear()
    }

    private fun initSearchBarListeners() {

        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchBar.addTextChangeListener(object : TextWatcher {
                private var searchFor = ""
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(string: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        subscriber.onNext(string.toString())
                }
            })
        })
            .map { text -> text.toLowerCase().trim() }
            .debounce(250, TimeUnit.MILLISECONDS)
            .distinct()
            .filter { text -> text.isNotBlank() && text.length > 2 }
            .subscribe { text -> listWeathersViewModel.onTextChanged(text) }

        searchBar.setSuggestionsClickListener(object : SuggestionsAdapter.OnItemViewClickListener {
            override fun OnItemDeleteListener(position: Int, v: View?) {
                searchBar.lastSuggestions.removeAt(position)
                searchBar.updateLastSuggestions(searchBar.lastSuggestions)
            }

            override fun OnItemClickListener(position: Int, v: View?) {
                searchBar.disableSearch()
                searchBar.text = searchBar.lastSuggestions[position].toString()
                searchBar.searchEditText.onEditorAction(EditorInfo.IME_ACTION_SEARCH)
            }
        })
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onButtonClicked(buttonCode: Int) {
            }

            override fun onSearchStateChanged(enabled: Boolean) {
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                searchBar.searchEditText.clearFocus()

                if (!text.isNullOrEmpty()) {
                    listWeathersViewModel.addNewLocation(text.toString())
                }

            }
        })
    }

}
