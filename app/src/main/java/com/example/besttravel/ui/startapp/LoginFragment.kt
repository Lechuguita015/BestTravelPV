package com.example.besttravel.ui.startapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.besttravel.R
import com.example.besttravel.ui.home.MenuHome
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var auth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        val tvDontAccount = view.findViewById<TextView>(R.id.dont_have_account)
        val btnLogIn = view.findViewById<Button>(R.id.bt_login)

        btnLogIn.setOnClickListener {
            val etEmail = view.findViewById<EditText>(R.id.et_email)
            val etPass = view.findViewById<EditText>(R.id.et_password)
            signIn(etEmail.text.toString(),etPass.text.toString())
        }
        tvDontAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun reload() {
        val intent = Intent(activity, MenuHome::class.java)
        startActivity(intent)
    }
    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
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
        // [END sign_in_with_email]
    }

}