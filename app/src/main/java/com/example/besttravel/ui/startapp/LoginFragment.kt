package com.example.besttravel.ui.startapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.navigation.fragment.findNavController
import com.example.besttravel.R
import com.example.besttravel.databinding.FragmentHotelsBinding
import com.example.besttravel.databinding.FragmentLoginBinding
import com.example.besttravel.ui.home.MenuHome
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater,container,false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.btLogin.setOnClickListener {
            signIn(binding.etEmail.text.toString(),binding.etPassword.text.toString())
        }
        binding.dontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun reload() {
        val intent = Intent(activity, MenuHome::class.java)
        startActivity(intent)
    }
    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        if (binding.etEmail.text.toString().isEmpty() || binding.etPassword.text.toString().isEmpty()) {
            Toast.makeText(activity, "The email and/or password are empty", Toast.LENGTH_SHORT).show()
            // [END sign_in_with_email]
        }else{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        reload()
                    } else {
                        val errorMessage = task.exception?.message ?: "Error al iniciar secion"
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

}