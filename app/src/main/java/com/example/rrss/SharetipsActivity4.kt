package com.example.rrss

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rrss.databinding.ActivitySharetips4Binding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SharetipsActivity4 : AppCompatActivity() {

    private val binding by lazy { ActivitySharetips4Binding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonTc1.setOnClickListener {
            setpassword()
            startActivity(Intent(this, SharetipsActivity::class.java))

        }

    }

    private fun setpassword(){

        val emailAddress = binding.editTextTc1.text.toString()

        Firebase.auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                }
            }


    }


}