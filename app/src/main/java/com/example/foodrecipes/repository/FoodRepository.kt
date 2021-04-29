package com.example.foodrecipes.repository

import com.example.foodrecipes.api.RetrofitService

class FoodRepository(private val retrofitService: RetrofitService) {

    fun getResponse(query: String) = retrofitService.getResponse(query)

    fun getCuisines(
        cuisine: String?,
        apiKey: String,
        number: Int,
        addRecipeInformation: Boolean,
        type: String,
        instructionsRequired: Boolean,
        addRecipeNutrition: Boolean
    ) = retrofitService.getCuisines(cuisine, apiKey, number,addRecipeInformation,type,instructionsRequired,addRecipeNutrition)


}