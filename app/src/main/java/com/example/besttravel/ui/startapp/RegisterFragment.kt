package com.example.besttravel.ui.startapp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.besttravel.R
import com.example.besttravel.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


open class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentRegisterBinding.inflate(inflater,container,false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        binding.btJoinUs.setOnClickListener {

            createAccount(binding.etEmailJoinus1.text.toString(),binding.etPasswordJoinus1.text.toString())
        }

        binding.cancel.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.haveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }


    }

    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        if (binding.etEmailJoinus1.text.toString().isEmpty() ||
            binding.etPasswordJoinus1.text.toString().isEmpty()){
            Toast.makeText(activity, "The email and/or password are empty", Toast.LENGTH_SHORT).show()
        }else{
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        findNavController().navigate(R.id.action_registerFragment_to_successfulFragment)
                        sendEmailVerification()
                    } else {
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            Toast.makeText(activity, "Esta cuenta ya está registrada. Por favor, intenta de nuevo.", Toast.LENGTH_SHORT).show()
                        } else {
                            findNavController().navigate(R.id.action_registerFragment_to_errorFragment)
                        }
                    }
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