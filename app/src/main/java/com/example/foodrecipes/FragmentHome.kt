package com.example.foodrecipes

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.adapters.HomeCuisinesAdapter
import com.example.foodrecipes.adapters.HomeCuisinesAdapter.OnItemClickListener
import com.example.foodrecipes.api.RetrofitService
import com.example.foodrecipes.model.ResultsHome
import com.example.foodrecipes.repository.FoodRepository
import com.example.foodrecipes.viewmodel.MainViewModel
import com.example.foodrecipes.viewmodel.MyViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentHome : Fragment(),OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val API_KEY="2e177bddd9444c04abd0981a584bfecb"


    lateinit var viewModel: MainViewModel
    lateinit var recyclerView:RecyclerView

    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recycler_view_home) as RecyclerView

        //adding a layoutmanager
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = HomeCuisinesAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProvider(this, MyViewModelFactory(FoodRepository(retrofitService))).get(MainViewModel::class.java)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter

        viewModel.responseListCuisines.observe(viewLifecycleOwner, Observer {
            Log.d("TAG", "onCreate: $it")
            adapter.setCuisinesList(it.results)
        })

        viewModel.getCuisines("indian",API_KEY)


    }

    override fun onItemClick(position: Int, sourceUrl: String) {
        val bundle = Bundle()
        bundle.putString("sourceUrl", sourceUrl)

        val recipeDetailFragment = RecipeDetailFragment()
        recipeDetailFragment.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, recipeDetailFragment)
            addToBackStack(null)
            commit()
        }
    }


}