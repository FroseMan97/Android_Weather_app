package com.iazarevsergey.lessons.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.iazarevsergey.lessons.R
import com.iazarevsergey.lessons.domain.model.Weather
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_list_item.view.*

class WeatherAdapter(private val onItemListener: OnWeathersItemClick) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    var items: List<Weather> = ArrayList()

    class ViewHolder(itemView: View, onItemListener: OnWeathersItemClick) : RecyclerView.ViewHolder(itemView){
        val condition_text = itemView.condition_text
        val temp_c = itemView.temp_c
        val feelsLike_c = itemView.feelsLike_c
        val city_text = itemView.city_text
        val icon = itemView.imageView
        val coordinates_text = itemView.coordinates_text
        val region_text = itemView.region_text
        val country_text = itemView.country_text

        fun bind(item:Weather, onItemListener: OnWeathersItemClick){
            itemView.setOnClickListener {
                onItemListener.onItemClick(item)
            }
        }

    }

    fun setNewItems(newItems: List<Weather>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.condition_text.text = items[position].condition_text
        holder.temp_c.text = items[position].weather_temp_c.toString() + " °C"
        holder.feelsLike_c.text = items[position].weather_feelslike_c.toString() + " °C"
        holder.city_text.text = items[position].location_name
        holder.coordinates_text.text = "${items[position].location_lat},${items[position].location_lon}"
        holder.region_text.text = "${items[position].location_region}"
        holder.country_text.text = "${items[position].location_country}"
        Picasso
            .get()
            .load("http:" + items[position].condition_icon)
            .resize(250, 250)
            .error(R.drawable.notification_bg_low_pressed)
            .into(holder.icon)
        holder.bind(items[position],onItemListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_item, parent, false), onItemListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    interface OnWeathersItemClick {
        fun onItemClick(item: Weather)
    }
}