package com.iazarevsergey.lessons.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.iazarevsergey.lessons.R
import com.iazarevsergey.lessons.domain.model.Weather
import com.iazarevsergey.lessons.factory.ViewModelFactory
import com.iazarevsergey.lessons.viewmodel.DetailWeatherViewModel
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_detail_weather.*
import javax.inject.Inject

class DetailWeatherFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    private lateinit var detailWeatherViewModel: DetailWeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        detailWeatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailWeatherViewModel::class.java)
        detailWeatherViewModel.getDetailWeather(arguments?.getString("selectedLocation"))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailToolBar.setupWithNavController(findNavController())

        update_button.setOnClickListener {
            detailWeatherViewModel.updateWeather()
        }

        detailWeatherViewModel.getWeather().observe(this, Observer {
            if (it == null) {
                setVisibilityUI(false)
            } else {
                setDataUI(it)
                setVisibilityUI(true)
            }
        })

        detailWeatherViewModel.getIsLoading().observe(this, Observer {
            setLoading(it)
            setVisibilityUI(!it)
        })

        detailWeatherViewModel.getInfo().observe(this, Observer {
            if (!it.isNullOrEmpty())
                showInfo(it)
        })

    }


    private fun setDataUI(currentWeather: Weather) {
        city.text = currentWeather.location_name
        last_updated.text = getString(R.string.last_update_text) + " " + currentWeather.weather_last_updated
        region_text.text =
            "${currentWeather.location_region}"
        country_text.text = "${currentWeather.location_country}"
        condition_text.text = currentWeather.condition_text
        temp_c.text = getString(R.string.temp_text) + " " + currentWeather.weather_temp_c + " °C"
        wind_kph.text =
            getString(R.string.wind_text) + " " + currentWeather.weather_wind_kph + " " + getString(R.string.speed_units_text)
        feelsLike_c.text = getString(R.string.feelslike_text) + " " + currentWeather.weather_feelslike_c + " °C"
        Picasso
            .get()
            .load("http:" + currentWeather.condition_icon)
            .resize(250, 250)
            .error(R.drawable.notification_bg_low_pressed)
            .into(weatherIcon)

    }

    private fun showInfo(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun setVisibilityUI(flag: Boolean) {
        val status = if (flag) View.VISIBLE else View.GONE
        update_button.visibility = status
        city.visibility = status
        last_updated.visibility = status
        region_text.visibility = status
        country_text.visibility = status
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

}
