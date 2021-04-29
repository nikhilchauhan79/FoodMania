package com.example.foodrecipes.viewmodel

import android.graphics.Movie
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipes.model.HomeCuisines
import com.example.foodrecipes.model.ResponseRec
import com.example.foodrecipes.model.Results
import com.example.foodrecipes.model.ResultsHome
import com.example.foodrecipes.repository.FoodRepository
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Query
import javax.security.auth.callback.Callback

class MainViewModel constructor(private val repository: FoodRepository)  : ViewModel() {

    val responseList = MutableLiveData<Results>()
    val responseListCuisines = MutableLiveData<HomeCuisines>()

    fun getResponse(query: String) {

        val response = repository.getResponse(query)
        response.enqueue(object :retrofit2.Callback<ResponseRec>{
            override fun onFailure(call: Call<ResponseRec>, t: Throwable) {
                Log.d("error", "onFailure: "+t.message)
            }

            override fun onResponse(call: Call<ResponseRec>, response: Response<ResponseRec>) {
                responseList.postValue(response.body()?.results)
            }

        })
    }

    fun getCuisines(cuisine: String?,apiKey:String){

        val response=repository.getCuisines(cuisine,apiKey,100,true,"main course",true,true)
        response.enqueue(object :retrofit2.Callback<HomeCuisines>{
            override fun onFailure(call: Call<HomeCuisines>, t: Throwable) {
                Log.d("error", "onFailure: "+t.message)
            }

            override fun onResponse(call: Call<HomeCuisines>, response: Response<HomeCuisines>) {
                responseListCuisines.postValue(response.body())
            }

        })
    }
}