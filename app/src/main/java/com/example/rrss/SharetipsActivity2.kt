package com.example.rrss

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rrss.databinding.ActivitySharetips2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SharetipsActivity2 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val binding by lazy { ActivitySharetips2Binding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.buttonTa1.setOnClickListener{
            login()

        }

        binding.buttonTa2.setOnClickListener{

            startActivity(Intent(this, SharetipsActivity4::class.java))

        }


    }

    private fun login() {

        var email : String = binding.editTextTa1.text.toString()
        var password : String = binding.editTextTa2.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && email != null && password != null) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    var intent : Intent = Intent(this, SharetipsActivity2::class.java)
                    startActivity(Intent(this, ContentsIndexActivity::class.java))

                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }


            }


    }


}