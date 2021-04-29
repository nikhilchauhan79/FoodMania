package com.example.foodrecipes.adapters

import android.graphics.Movie
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.example.foodrecipes.R
import com.example.foodrecipes.model.ResultsHome
import kotlinx.android.synthetic.main.dish_card.view.*
import com.bumptech.glide.module.AppGlideModule
@GlideModule
class AppGlideModule : AppGlideModule()

class HomeCuisinesAdapter() : RecyclerView.Adapter<HomeCuisinesAdapter.ViewHolder>() {
    var cuisines = mutableListOf<ResultsHome>()

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCuisinesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.dish_card, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: HomeCuisinesAdapter.ViewHolder, position: Int) {
        holder.bindItems(cuisines[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return cuisines.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(cuisine: ResultsHome) {
            val textViewCredit = itemView.findViewById(R.id.home_credits_text) as TextView
            val textViewTitle = itemView.findViewById(R.id.home_title) as TextView
            val textViewMinutes = itemView.findViewById(R.id.home_readyInMinutes) as TextView
            val textViewSource = itemView.findViewById(R.id.home_source_name) as TextView
            val textViewNutrients = itemView.findViewById(R.id.home_nutrients) as TextView
            val textViewSmartPoints = itemView.findViewById(R.id.home_weightWatcherSmartPoints) as TextView
            val textViewHealthScore = itemView.findViewById(R.id.health_score) as TextView

            textViewCredit.text = cuisine.creditsText
            textViewMinutes.text = cuisine.readyInMinutes.toString()
            textViewSource.text = cuisine.sourceName
            textViewTitle.text = cuisine.title

            val nut=cuisine.nutrition?.nutrientsHome
            if (nut != null) {
                for(n in nut){
                    Log.d("title", "bindItems: "+n.title)
                    textViewNutrients.append("name "+n.title)
                    textViewNutrients.append("Amount"+n.amount)
                    textViewNutrients.append("name"+n.unit)
                    textViewNutrients.append("name"+n.percentOfDailyNeeds)

                }
            }


            Log.d("TAG", "bindItems: "+cuisine.weightWatcherSmartPoints.toString())
            textViewSmartPoints.text = cuisine.weightWatcherSmartPoints.toString()
            textViewHealthScore.text = cuisine.healthScore.toString()

            Glide
                .with(itemView.context)
                .load(cuisine.image)
                .centerCrop()
                .into(itemView.image_view_dish_card);


        }
    }

    fun setCuisinesList(cuisines: List<ResultsHome>) {
        this.cuisines = cuisines.toMutableList()
        notifyDataSetChanged()
    }


}