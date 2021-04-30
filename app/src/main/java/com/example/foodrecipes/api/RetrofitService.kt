package com.example.foodrecipes.api

import android.telecom.Call
import com.example.foodrecipes.model.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("complexSearch")
    fun getResponse(@Query("query") query: String?): retrofit2.Call<ResponseRec>

    @GET("findByIngredients")
    fun searchRecipes(
        @Query("apiKey") apiKey: String,
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int,

    ): retrofit2.Call<List<SearchResponse>>?

    @GET("complexSearch")
    fun getCuisines(
        @Query("cuisine") cuisine: String?,
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int,
        @Query("addRecipeInformation") addRecipeInformation: Boolean,
        @Query("type") type: String,
        @Query("instructionsRequired") instructionsRequired: Boolean,
        @Query("addRecipeNutrition") addRecipeNutrition: Boolean
    ): retrofit2.Call<HomeCuisines>


    companion object {

        var retrofitService: RetrofitService? = null

        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.spoonacular.com/recipes/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}