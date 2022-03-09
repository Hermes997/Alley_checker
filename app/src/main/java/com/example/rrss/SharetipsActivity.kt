package com.example.rrss

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rrss.databinding.ActivitySharetipsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase


class SharetipsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val binding by lazy { ActivitySharetipsBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.buttonT1.setOnClickListener{
            signup()
            startActivity(Intent(this, SharetipsActivity2::class.java))

        }
        binding.buttonT2.setOnClickListener{startActivity(Intent(this, SharetipsActivity2::class.java))}


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(Intent(this, ContentsIndexActivity::class.java))
        }
    }

    private fun signup(){

        var email : String = binding.editTextT1.text.toString()
        var password : String = binding.editTextT2.text.toString()


        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = Firebase.auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = "null"
                    }

                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "User profile updated.")
                            }
                        }
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

}