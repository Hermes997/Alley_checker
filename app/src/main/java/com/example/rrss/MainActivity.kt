package com.example.rrss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.rrss.databinding.ActivityMainBinding
import com.example.rrss.databinding.ActivityStatusBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.buttonM1.setOnClickListener{startActivity(Intent(this, HowActivity::class.java))}
        binding.buttonM2.setOnClickListener{startActivity(Intent(this, StatusActivity::class.java))}
        binding.buttonM3.setOnClickListener{startActivity(Intent(this, CalendarActivity::class.java))}
        binding.buttonM4.setOnClickListener{startActivity(Intent(this, SharetipsActivity::class.java))}


    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCompat.finishAffinity(this)
        exitProcess(0)
    }

}