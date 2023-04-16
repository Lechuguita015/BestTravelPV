package com.example.besttravel.ui.startapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.besttravel.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


open class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var auth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        val tvCancel = view.findViewById<TextView>(R.id.cancel)
        val tvHaveAccount = view.findViewById<TextView>(R.id.have_account)
        val etUserName = view.findViewById<EditText>(R.id.et_username)
        val btnJoinUS = view.findViewById<Button>(R.id.bt_join_us)
        val etSendEmail = view.findViewById<EditText>(R.id.et_email_joinus1)
        val etSendPass = view.findViewById<EditText>(R.id.et_password_joinus1)

        btnJoinUS.setOnClickListener {
            val etEmail = view.findViewById<EditText>(R.id.et_email_joinus1)
            val etPass = view.findViewById<EditText>(R.id.et_password_joinus1)
            createAccount(etEmail.text.toString(),etPass.text.toString())
        }

        tvCancel.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        tvHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    findNavController().navigate(R.id.action_registerFragment_to_successfulFragment)
                    sendEmailVerification()
                } else {
                    // If sign in fails, display a message to the user.
                    findNavController().navigate(R.id.action_registerFragment_to_errorFragment)
                }
            }
        // [END create_user_with_email]
    }
    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()

    }

}