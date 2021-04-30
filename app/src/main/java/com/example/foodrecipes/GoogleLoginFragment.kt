package com.example.foodrecipes


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_google_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.withContext


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [GoogleLoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GoogleLoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Initialize Firebase Auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }!!
        // [END config_signin]


        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_google_login, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
//        register_text_view.setOnClickListener {

//            val navController = Navigation.findNavController(view)

//            if (navController.currentDestination?.id == R.id.googleLoginFragment) {
//                navController.navigate(R.id.action_googleLoginFragment_to_registerFragment)
//            }


//            val action=GoogleLoginFragmentDirections.actionGoogleLoginFragmentToRegisterFragment()
//            findNavController().navigate(action)


//            val action =
//                GoogleLoginFragmentDirections
//                    .actionGoogleLoginFragmentToRegisterFragment()
//            it.findNavController().apply {
//                navigateUp()
//                navigate(action)
//            }

//        }
        register_text_view.setOnClickListener {

//            val navHostFragment =
//                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//            val navController = navHostFragment.navController
//            val action =
//                GoogleLoginFragmentDirections.actionGoogleLoginFragmentToRegisterFragment()
//            Navigation.findNavController(view).navigate(action);
//            val navController = findNavController()
//            navController.navigate(R.id.action_googleLoginFragment_to_registerFragment)
//            navController?.navigateUp()
        }
        auth = Firebase.auth

        login_button.setOnClickListener {
            auth = FirebaseAuth.getInstance()

            if (email_address.text?.trim().toString()
                    .isNotEmpty() || password.text?.trim().toString().isNotEmpty()
            ) {
//                createUser(
//                    email_address_register.text?.trim().toString(),
//                    password_register.text?.trim().toString()
//                )
                signInUser(email_address.text?.trim().toString(), password.text?.trim().toString())
            } else {
                Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT)
                    .show()

                signIn()
            }
        }

    }

    fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInUser: "+task.result.toString())
//                    Toast.makeText(this,"Sign in successful",Toast.LENGTH_SHORT).show()
                } else {
//                    Toast.makeText(this,"Sign in successful",Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "signInUser: unsuccessful "+task.exception.toString())
                }
            }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {task->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }


    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    private fun updateUI(user: FirebaseUser?) {
        Toast.makeText(context,"You are already logged in",Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}


//fun createUser(email: String, password: String) {
//    auth.createUserWithEmailAndPassword(email, password)
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Log.d("success", "createUser: " + "task successful")
//            } else {
//                Log.d("failed", "createUser: " + "task failed" + task.exception)
//
//            }
//        }
//
//}


// [END auth_with_google]
//
//    // [START signin]
//    private fun signIn() {
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
// [END signin]

