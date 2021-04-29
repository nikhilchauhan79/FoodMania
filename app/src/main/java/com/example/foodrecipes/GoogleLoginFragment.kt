package com.example.foodrecipes




import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_google_login.*
import kotlinx.android.synthetic.main.fragment_register.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var auth: FirebaseAuth


/**
 * A simple [Fragment] subclass.
 * Use the [GoogleLoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GoogleLoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()


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

            Navigation.findNavController(requireView()).navigate(R.id.action_googleLoginFragment_to_registerFragment);

        }

        login_button.setOnClickListener {

            if (email_address.text?.trim().toString()
                    .isNotEmpty() || password.text?.trim().toString().isNotEmpty()
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


//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithCredential:success")
//                    val user = auth.currentUser
//                    updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    updateUI(null)
//                }
//            }
//    }
//    // [END auth_with_google]
//
//    // [START signin]
//    private fun signIn() {
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
    // [END signin]
