package com.example.foodrecipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.adapters.SearchResultAdapter
import com.example.foodrecipes.api.RetrofitService
import com.example.foodrecipes.model.SearchRecipesData
import com.example.foodrecipes.repository.FoodRepository
import com.example.foodrecipes.viewmodel.MainViewModel
import com.example.foodrecipes.viewmodel.MyViewModelFactory
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var searchView: SearchView

    val API_KEY="2e177bddd9444c04abd0981a584bfecb"

    lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView

    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchView=view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.recycler_view_search_parent) as RecyclerView


        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val adapter = SearchResultAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProvider(this, MyViewModelFactory(FoodRepository(retrofitService))).get(MainViewModel::class.java)


        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter

        viewModel.searchResponseList.observe(viewLifecycleOwner, Observer {
            Log.d("TAG", "onCreate: $it")

            val gson = Gson()

//            val jsonarray = JSONArray(it)

            for (i in 0 until it.size()) {
//                val jsonobject: JSONObject = jsonarray.getJSONObject(i)
                val recipeData: SearchRecipesData = gson.fromJson(it.get(i), SearchRecipesData::class.java) //input is your String
                adapter.addCuisines(recipeData)
            }

        })


        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {


                searchView.queryHint="Enter comma separated list of ingre. to include in dish"

                if(query?.contains(",")==false && query.length>0){
                    viewModel.searchRecipes(API_KEY, query,2)
                    return true

                }
                val empty = query?.filter { !it.isWhitespace() }?.split(",")

                if(empty?.isNullOrEmpty()!!){
                    Toast.makeText(context,"Please enter one or more ingredients separated by commas",Toast.LENGTH_SHORT).show()
                    return true
                }

                val text = filterQuery(empty as ArrayList<String>)
//                println(text)

                if (text != null) {
                    viewModel.searchRecipes(API_KEY, text,2)
                }


                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false

            }

        })


    }

    fun filterQuery(newString: ArrayList<String>):String? {
        var newStr=""
        for ((index, element) in newString.withIndex()) {
            println("$index -> $element")
            if(index==0){
                newStr+=element+","
            }else {
                if(index==newString.lastIndex){
                    newStr += "+" + element
                    return newStr

                }
                newStr += "+" + element+","
            }

        }
        return newStr
    }
}