package com.example.foodrecipes

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var auth: FirebaseAuth

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        auth = FirebaseAuth.getInstance()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_register, container, false)

//        view.login_text_view.setOnClickListener {
//            Log.d("login button", "onCreateView: "+"clicked")
//            }


        return view
    }


    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

//        updateUI(currentUser)
    }

    fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("success", "createUser: " + "task successful")
                } else {
                    Log.d("failed", "createUser: " + "task failed" + task.exception)

                }
            }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        login_text_view.setOnClickListener {
//            val action=RegisterFragmentDirections.actionRegisterFragmentToGoogleLoginFragment()
//            findNavController().navigate(action)

//            val navController = Navigation.findNavController(view)
//            navController.navigate(R.id.action_googleLoginFragment_to_registerFragment)

//            val action =
//                RegisterFragmentDirections
//                    .actionRegisterFragmentToGoogleLoginFragment()
//            it.findNavController().apply {
//                navigateUp()
//                navigate(action)
//            }
//        }

        login_text_view.setOnClickListener {
//            val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//            val navController = navHostFragment.navController

            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_registerFragment_to_googleLoginFragment)

        }

        register_button.setOnClickListener {

            if (email_address_register.text?.trim().toString()
                    .isNotEmpty() || password_register.text?.trim().toString().isNotEmpty()
            ) {
                createUser(
                    email_address_register.text?.trim().toString(),
                    password_register.text?.trim().toString()
                )
            } else {
                Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

}