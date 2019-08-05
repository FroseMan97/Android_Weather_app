package com.iazarevsergey.lessons.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iazarevsergey.lessons.R
import com.iazarevsergey.lessons.domain.model.CurrentWeather
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_list_item.view.*

class WeatherAdapter(private val onItemListener:OnItemListener) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private var items: List<CurrentWeather> = ArrayList()

    fun getItems() = items

    class ViewHolder(itemView: View, onItemListener:OnItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val condition_text = itemView.condition_text
        val temp_c = itemView.temp_c
        val feelsLike_c = itemView.feelsLike_c
        val city_text = itemView.city_text
        val icon = itemView.imageView
        val coordinates_text = itemView.coordinates_text
        val mOnItemListener:OnItemListener = onItemListener

        init {
            itemView.setOnClickListener(this)
        }


        override fun onClick(view: View?) {
            mOnItemListener.onItemClick(adapterPosition)
        }
    }

    fun setNewItems(newItems: List<CurrentWeather>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.condition_text.text = items[position].condition_text
        holder.temp_c.text = items[position].weather_temp_c.toString() + " °C"
        holder.feelsLike_c.text = items[position].weather_feelslike_c.toString() + " °C"
        holder.city_text.text = items[position].location_name
        holder.coordinates_text.text = "${items[position].location_lat},${items[position].location_lon}"

        Picasso
            .get()
            .load("http:" + items[position].condition_icon)
            .resize(250, 250)
            .error(R.drawable.notification_bg_low_pressed)
            .into(holder.icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_item, parent, false), onItemListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    interface OnItemListener{
        fun onItemClick(position:Int)
    }
}