package com.example.foodrecipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.adapters.HomeCuisinesAdapter
import com.example.foodrecipes.adapters.SearchResultAdapter
import com.example.foodrecipes.api.RetrofitService
import com.example.foodrecipes.repository.FoodRepository
import com.example.foodrecipes.viewmodel.MainViewModel
import com.example.foodrecipes.viewmodel.MyViewModelFactory

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

    val API_KEY="3df53512b59f4a70930a953af1763e19"

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

        viewModel.searchRecipes(API_KEY, "apples,+flour,+sugar",2)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter

        viewModel.searchResponseList.observe(viewLifecycleOwner, Observer {
            Log.d("TAG", "onCreate: $it")
            adapter.setCuisinesList(it)
        })


        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false

            }

        })


    }
}