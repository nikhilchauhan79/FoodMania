package com.example.foodrecipes.model

import com.google.gson.annotations.SerializedName

//data class SearchResponse(val searchResponse:JsonArray?)

//data class List<SearchResponse>(val searchResponse:List<SearchRecipesData>?)

data class SearchRecipesData(
    val id: Int?, val image: String?, val likes: Int?, val missedIngredientCount: Int?,
    @SerializedName("missedIngredients")
    val missedIngredients: List<Ingredients>?,
    val title: String?,
    val usedIngredientCount: Int?,

    @SerializedName("usedIngredients")
    val usedIngredients: List<Ingredients>?
)

data class Ingredients(
    val aisle: String?,
    val amount: Int?,
    val id: Int?,
    val image: String?,
    val name: String?,
    val original: String?,
    val originalName: String?,
    val unit: String?,
    val unitLong: String?,
    val unitShort: String?
)