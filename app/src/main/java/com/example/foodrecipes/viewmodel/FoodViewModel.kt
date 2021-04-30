package com.example.foodrecipes.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipes.model.HomeCuisines
import com.example.foodrecipes.model.ResponseRec
import com.example.foodrecipes.model.Results
import com.example.foodrecipes.model.SearchResponse
import com.example.foodrecipes.repository.FoodRepository
import com.google.android.play.core.internal.t
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: FoodRepository) : ViewModel() {

    val responseList = MutableLiveData<Results>()
    val responseListCuisines = MutableLiveData<HomeCuisines>()
    val searchResponseList = MutableLiveData<List<SearchResponse>>()

    fun getResponse(query: String) {

        val response = repository.getResponse(query)
        response.enqueue(object : retrofit2.Callback<ResponseRec> {
            override fun onFailure(call: Call<ResponseRec>, t: Throwable) {
                Log.d("error", "onFailure: " + t.message)
            }

            override fun onResponse(call: Call<ResponseRec>, response: Response<ResponseRec>) {
                responseList.postValue(response.body()?.results)
            }

        })
    }

    fun getCuisines(cuisine: String?, apiKey: String) {

        val response = repository.getCuisines(cuisine, apiKey, 100, true, "main course", true, true)
        response.enqueue(object : retrofit2.Callback<HomeCuisines> {
            override fun onFailure(call: Call<HomeCuisines>, t: Throwable) {
                Log.d("error", "onFailure: " + t.message)
            }

            override fun onResponse(call: Call<HomeCuisines>, response: Response<HomeCuisines>) {
                responseListCuisines.postValue(response.body())
            }

        })
    }

    fun searchRecipes(apiKey: String, ingredients: String, number: Int) {
        val response = repository.searchRecipes(apiKey, ingredients, number)
        response?.enqueue(object :Callback<List<SearchResponse>>{
            override fun onFailure(call: Call<List<SearchResponse>>?, t: Throwable) {
                Log.d("error", "onFailure: " + t.message)
            }

            override fun onResponse(
                call: Call<List<SearchResponse>>?,
                response: Response<List<SearchResponse>>?
            ) {
                Log.d("response", "onResponse: "+response?.body())

                var str_response = response?.body()!!.get(0).searchResponse
                searchResponseList.postValue(str_response)

                //creating json object
                val json_contact:JSONObject = JSONObject(str_response)
                //creating json array
                var jsonarray_info: JSONArray = json_contact.getJSONArray("info")
            }

        })

    }
}
