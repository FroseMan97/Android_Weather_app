package com.iazarevsergey.lessons.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iazarevsergey.lessons.R
import com.iazarevsergey.lessons.domain.model.CurrentWeather
import com.iazarevsergey.lessons.factory.ViewModelFactory
import com.iazarevsergey.lessons.viewmodel.WeatherViewModel
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.activity_weather.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class WeatherActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_weather)

        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel::class.java)

        initDataObservers()

        initSearchBarListeners()

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
                    if (currentFocus == searchBar.searchEditText) {
                        subscriber.onNext(string.toString())
                    }
                }
            })
        })
            .map { text -> text.toLowerCase().trim() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinct()
            .filter { text -> text.isNotBlank() }
            .subscribe { text ->
                weatherViewModel.onTextChanged(text)
            }

        searchBar.setSuggestionsClickListener(object : SuggestionsAdapter.OnItemViewClickListener {
            override fun OnItemDeleteListener(position: Int, v: View?) {
                searchBar.lastSuggestions.removeAt(position)
                searchBar.updateLastSuggestions(searchBar.lastSuggestions)
            }

            override fun OnItemClickListener(position: Int, v: View?) {
                searchBar.searchEditText.clearFocus()
                searchBar.text = searchBar.lastSuggestions[position].toString()
                searchBar.searchEditText.onEditorAction(IME_ACTION_SEARCH)
            }
        })

        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onButtonClicked(buttonCode: Int) {
            }

            override fun onSearchStateChanged(enabled: Boolean) {
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                searchBar.searchEditText.clearFocus()
                weatherViewModel.onSearchConfirmed(text.toString())
            }
        })
    }

    private fun initDataObservers() {
        weatherViewModel.getSearches().observe(this, Observer {
            if (searchBar.isSearchEnabled) {
                searchBar.updateLastSuggestions(it)
            } else {
                searchBar.lastSuggestions = it
            }
        })

        weatherViewModel.getWeather().observe(this, Observer {
            if (it == null) {
                setVisibilityUI(false)
            } else {
                setDataUI(it)
                setVisibilityUI(true)
            }
        })

        weatherViewModel.getIsLoading().observe(this, Observer {
            setLoading(it)

        })

        weatherViewModel.getOnError().observe(this, Observer {
            showError(it)
        })
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun setVisibilityUI(flag: Boolean) {
        val status = if (flag) View.VISIBLE else View.GONE
        city.visibility = status
        last_updated.visibility = status
        region.visibility = status
        condition_text.visibility = status
        temp_c.visibility = status
        wind_kph.visibility = status
        feelsLike_c.visibility = status
        weatherIcon.visibility = status
    }

    private fun setLoading(flag: Boolean) {
        when (flag) {
            true -> {
                progressBar.visibility = View.VISIBLE
            }
            false -> {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun setDataUI(currentWeather: CurrentWeather) {
        city.text = currentWeather.location_name
        last_updated.text = "Последнее обновление " + currentWeather.weather_last_updated
        region.text = "Регион " + currentWeather.location_region + ", " + currentWeather.location_country
        condition_text.text = currentWeather.condition_text
        temp_c.text = currentWeather.weather_temp_c.toString() + " °C"
        wind_kph.text = "Скорость ветра " + currentWeather.weather_wind_kph.toString() + " км/ч"
        feelsLike_c.text = "Ощущается как " + currentWeather.weather_feelslike_c.toString() + " °C"
        Picasso
            .get()
            .load("http:" + currentWeather.condition_icon)
            .resize(250, 250)
            .error(R.drawable.notification_bg_low_pressed)
            .into(weatherIcon)

    }
}
