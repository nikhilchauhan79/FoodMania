package com.example.foodrecipes.adapters

import android.graphics.Movie
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.example.foodrecipes.R
import com.example.foodrecipes.model.ResultsHome
import kotlinx.android.synthetic.main.dish_card.view.*
import com.bumptech.glide.module.AppGlideModule
import com.example.foodrecipes.model.SearchRecipesData
import com.example.foodrecipes.model.SearchResponse
import kotlinx.android.synthetic.main.search_result_card.view.*


class SearchResultAdapter() : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {
    var searchResultsList = mutableListOf<SearchResponse>()
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.search_result_card, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: SearchResultAdapter.ViewHolder, position: Int) {
        searchResultsList[0].searchResponse?.get(position)?.let { holder.bindItems(it) }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return searchResultsList.size
    }

    //the class is hodling the list view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(searchRecipesData: SearchRecipesData) {

            Log.d("title", "bindItems: "+searchRecipesData.title)
            itemView.search_card_title.text = searchRecipesData.title
            itemView.search_card_miss_count.text = searchRecipesData.missedIngredientCount.toString()
            itemView.search_card_used_count.text = searchRecipesData.usedIngredientCount.toString()




            Glide
                .with(itemView.context)
                .load(searchRecipesData.image)
                .centerCrop()
                .into(itemView.image_view_search_card);



        }



    }

    fun setCuisinesList(searchRecipesData: List<SearchResponse>) {
        this.searchResultsList = searchRecipesData.toMutableList()
        notifyDataSetChanged()
    }



}