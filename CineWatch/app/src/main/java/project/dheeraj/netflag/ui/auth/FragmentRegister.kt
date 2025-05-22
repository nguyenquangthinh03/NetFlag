package project.dheeraj.netflag.ui.auth

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import project.dheeraj.netflag.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.AndroidEntryPoint
import project.dheeraj.netflag.ui.main.MainActivity
import javax.inject.Inject

@AndroidEntryPoint
class FragmentRegister : Fragment() {
    private lateinit var usernameEditText: TextInputEditText
    @Inject lateinit var mAuth:FirebaseAuth
    @Inject lateinit var fire:FirebaseFirestore
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var cnfPasswordEditText: TextInputEditText
    private lateinit var warningTextView: TextView
    private lateinit var sharedPreferences:SharedPreferences

    //  Remove any extra spacing from the email address
    private fun formatUsername(username:String) : String{
        return username.filter {
            !it.isWhitespace()
        }
    }

    //  Register the user
    @SuppressLint("SetTextI18n")
    private fun registerUser(){
        var username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        username = formatUsername(username)
        usernameEditText.setText(username)

        if(username.isNotEmpty() and password.isNotEmpty()){
            mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val email = mAuth.currentUser?.email.orEmpty()
                        val data = mapOf("email" to email)
                        fire.collection("users")
                            .document(mAuth.currentUser!!.uid)
                            .set(data, SetOptions.merge())
                            .addOnSuccessListener {
                            }
                            .addOnFailureListener { exception ->
                                Log.i("REGISTER", exception.toString())
                            }
                        Toast.makeText(context, "User registered successfully!", Toast.LENGTH_SHORT).show()
                        MainActivity.start(requireContext())
                        requireActivity().finish()
                    } else {
                        warningTextView.text = "Please enter valid credentials!"
                        warningTextView.visibility = View.VISIBLE
                    }
                }
        }
        else{
            Toast.makeText(context, "Invalid value", Toast.LENGTH_SHORT).show()
        }
    }

    //  Confirm the password
    @SuppressLint("SetTextI18n")
    private fun validate():Boolean{
        if(passwordEditText.text.toString() == cnfPasswordEditText.text.toString())
            return true
        warningTextView.text = "Password doesn't match!"
        warningTextView.visibility = View.VISIBLE
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        usernameEditText = view.findViewById(R.id.emailInputRegister)
        passwordEditText = view.findViewById(R.id.passwordInputRegister)
        cnfPasswordEditText = view.findViewById(R.id.passwordConfirmInput2)
        warningTextView = view.findViewById(R.id.alertTextRegister)
        mAuth = FirebaseAuth.getInstance()
        fire = FirebaseFirestore.getInstance()
        sharedPreferences =  context?.getSharedPreferences("project.dheeraj.netflag.auth", 0)!!

        view.findViewById<Button>(R.id.SignUpButtonRegister).setOnClickListener {
            if(validate()){
                registerUser()
            }

        }
        view.findViewById<TextView>(R.id.signInTextRegister).setOnClickListener {
            findNavController().navigate(R.id.action_fragmentRegister_to_fragmentLogin)
        }


        return view
    }
}