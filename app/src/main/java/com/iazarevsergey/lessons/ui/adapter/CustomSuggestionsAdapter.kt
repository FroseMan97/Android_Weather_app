package com.iazarevsergey.lessons.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iazarevsergey.lessons.R
import com.iazarevsergey.lessons.domain.model.Search
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import kotlinx.android.synthetic.main.suggestions_item.view.*

class CustomSuggestionsAdapter(inflater: LayoutInflater, private val onItemViewClickListener: OnItemViewClickListener) : SuggestionsAdapter<Search, SuggestionHolder>(inflater)  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionHolder {
        return SuggestionHolder(LayoutInflater.from(parent.context).inflate(R.layout.suggestions_item, parent, false), onItemViewClickListener)
    }

    override fun getSingleViewHeight(): Int {
        return 80
    }

    override fun onBindSuggestionHolder(suggestion: Search, holder: SuggestionHolder, position: Int) {
        holder.location_title.text = suggestion.name
        holder.location_coordinates.text = suggestion.coordinates
    }

    interface OnItemViewClickListener{
        fun OnItemClickListener(position:Int)
    }

}

class SuggestionHolder(itemView: View, OnItemViewClickListener:CustomSuggestionsAdapter.OnItemViewClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
    val location_title = itemView.location_name
    val location_coordinates = itemView.location_coordinates
    val mOnItemViewClickListener: CustomSuggestionsAdapter.OnItemViewClickListener = OnItemViewClickListener
    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        mOnItemViewClickListener.OnItemClickListener(adapterPosition)
    }
}