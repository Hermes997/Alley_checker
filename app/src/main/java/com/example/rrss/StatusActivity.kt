package com.example.rrss

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.rrss.databinding.ActivityStatusBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*


class StatusActivity : AppCompatActivity() {


    private val binding by lazy { ActivityStatusBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch(Dispatchers.Default) {


        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar1, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val scope1 = CoroutineScope(Dispatchers.Main)


        when (item.itemId) {
            R.id.map -> {
                startActivity(Intent(this, MapsActivity::class.java))

            }

            R.id.trashcan1 -> {
                    readValue()
                    binding.textViewS11.text = "#산격동1"

            }

            R.id.trashcan2 -> {

                scope1.launch(Dispatchers.Default) {

                }
            }

            R.id.trashcan3 -> {

                scope1.launch(Dispatchers.Default) {

                }

            }

        }
        return super.onOptionsItemSelected(item)
    }


    fun readValue() {
        val database = Firebase.database
        val myRef = database.getReference("Level/Data")
        var data : String = "null%"
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                        data = snapshot.value.toString() + "%"
                        binding.textViewS1.text = data
                    }
                }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



    }



}




