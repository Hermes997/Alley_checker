package com.example.rrss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.rrss.databinding.ActivityReportsIndexBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ReportsIndexActivity : AppCompatActivity() {

    private val binding by lazy {ActivityReportsIndexBinding.inflate(layoutInflater)}
    private val itemList = ArrayList<WriteInfo>()
    private var db = FirebaseFirestore.getInstance()
    private var user : FirebaseUser? =  FirebaseAuth.getInstance().currentUser
    private val profileImage = Firebase.storage.getReference("profileImages/${user?.uid}").downloadUrl
    private var docPathList = ArrayList<String>()
    private val reportsFlag : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonTj2.text = user?.displayName
        binding.buttonTj2.text = FirebaseAuth.getInstance().currentUser?.displayName


        profileImage.addOnSuccessListener {
            Glide.with(this).load(it).into(binding.imageViewTj1)
        }.addOnFailureListener {  }

        binding.buttonTj1.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.buttonTj2.setOnClickListener{
            startActivity(Intent(this, SharetipsActivity5::class.java))
        }

        binding.buttonTj3.setOnClickListener{
            startActivity(Intent(this, ContentsIndexActivity::class.java))
        }

        binding.floatingActionButtonTj1.setOnClickListener {
            val intent = Intent(this, SharetipsActivity6::class.java)
            intent.putExtra("flag", reportsFlag)
            startActivity(intent)
        }

        represent()

        binding.swipeTj1.setOnRefreshListener {
            represent()
            binding.swipeTj1.isRefreshing = false
        }

    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }


    private fun represent(){
        itemList.clear()
        val blindError = RecyclerViewAdapter(itemList, docPathList, reportsFlag)
        db.collection("reports").orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                itemList.clear()
                for (document in result){
                    val item = WriteInfo(
                        document["title"] as String,
                        document["contents"] as String,
                        document["publisher"] as String,
                        document["date"] as String,
                        document["date1"] as String,
                        document["imageUri"] as ArrayList<String>?,
                        document["uid"] as String)
                    itemList.add(item)
                    docPathList.add(document.id)
                }
                val adapter = RecyclerViewAdapter(itemList, docPathList, reportsFlag)
                binding.recyclerViewTj1.adapter = adapter
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
            }

        binding.recyclerViewTj1.adapter = blindError

    }



}