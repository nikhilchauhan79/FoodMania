package com.example.foodrecipes.model

import com.google.gson.annotations.SerializedName

data class HomeCuisines(val results: List<ResultsHome>)

data class ResultsHome(val title:String?,val readyInMinutes:Int?,val servings:Int?,val sourceUrl:String?,val pricePerServing:Float?,val sourceName:String?,val creditsText:String?,
val weightWatcherSmartPoints:Int?,val healthScore:Int?,val license:String?,
                       @SerializedName("nutrition")
                       val nutrition:NutritionHome?,
                       val image:String)

data class NutritionHome(@SerializedName("nutrients")
                         val nutrientsHome:List<NutrientsHome>?)

data class NutrientsHome(val title:String?,val amount:Float?,val unit:String?,val percentOfDailyNeeds:String?)