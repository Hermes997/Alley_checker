package com.example.rrss

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.example.rrss.databinding.ActivitySharetips6Binding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

class SharetipsActivity6 : AppCompatActivity() {

    private var user : FirebaseUser? = null
    private var imageList = ArrayList<Uri?>()
    private var imageAmount : Int = 0
    private val binding by lazy { ActivitySharetips6Binding.inflate(layoutInflater)}
    private val storageRef = Firebase.storage.reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val flag = intent.getIntExtra("flag", 0)
        
        binding.buttonTe2.setOnClickListener {
            val title : String? = binding.editTextTe1.text.toString()
            val contents : String? = binding.editTextTe2.text.toString()
            var intent = Intent(this, AddMemoryActivity::class.java)
            intent.putExtra("writeInfo1", title)
            intent.putExtra("writeInfo2", contents)
            intent.putExtra("flag", flag)
            startActivity(intent)
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        val flag = intent.getIntExtra("flag", 0)

        if(intent.hasExtra("writeInfo1") && intent.hasExtra("writeInfo2")){
            binding.editTextTe1.setText(intent.getStringExtra("writeInfo1"))
            binding.editTextTe2.setText(intent.getStringExtra("writeInfo2"))
        }

        if(intent.hasExtra("imageAmount")){
            imageAmount = intent.getIntExtra("imageAmount",0)
            binding.textViewTe2.text = "첨부된 이미지 : $imageAmount"
        }

        for (i in 0 until imageAmount) {
            imageList.add(intent.getParcelableExtra("imageUri${i}"))
        }

        binding.buttonTe1.setOnClickListener {

            uploadContent(flag)

        }

    }
    private fun uploadContent(flag : Int) {

        val finalTitle : String = binding.editTextTe1.text.toString()
        val finalContent: String = binding.editTextTe2.text.toString()
        user = FirebaseAuth.getInstance().currentUser
        val finalPublisher : String = user?.displayName.toString()
        val long_now = System.currentTimeMillis()
        val t_date = Date(long_now)
        val t_dataFormat = SimpleDateFormat("yy-MM-dd hh:mm:ss E", Locale("ko", "KR"))
        val t_dataFormat1 = SimpleDateFormat("yyMMddhhmmsssss", Locale("ko", "KR"))
        val str_date = t_dataFormat.format(t_date)
        val str_date1 = t_dataFormat1.format(t_date)
        var imgUri = ArrayList<String>()
        val uid = user?.uid.toString()



        when(flag){

            0 -> {
                for (i in 0 until imageAmount) {
                    val imageRef = storageRef.child("postsImages/${str_date1}/${i}")
                    val uploadTask = imageRef.putFile(imageList[i]!!)
                    uploadTask.addOnFailureListener{}.addOnCompleteListener{}
                    imgUri?.add(imageRef.toString())
                }
            }

            1 -> {
                for (i in 0 until imageAmount) {
                    val imageRef = storageRef.child("reportsImages/${str_date1}/${i}")
                    val uploadTask = imageRef.putFile(imageList[i]!!)
                    uploadTask.addOnFailureListener{}.addOnCompleteListener{}
                    imgUri?.add(imageRef.toString())
                }
            }
        }



        var writeInfo = WriteInfo(finalTitle, finalContent, finalPublisher, str_date, str_date1, imgUri, uid)
        uploader(writeInfo, flag)
    }

    private fun uploader(writeInfo : WriteInfo, flag : Int){
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        when(flag){
            0 -> {
                db.collection("posts")
                    .add(writeInfo)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        confirm(flag)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }

            1 -> {
                db.collection("reports")
                    .add(writeInfo)
                    .addOnSuccessListener { documentReference ->
                        confirm(flag)
                        db.collection("reports")
                            .document(documentReference.id)
                            .update(mapOf("reportsState" to 0))
                            .addOnSuccessListener {
                            }
                            .addOnFailureListener {
                            }
                    }
                    .addOnFailureListener { e ->

                    }


            }



        }


    }

    private fun confirm(flag: Int){
        val alert = AlertDialog.Builder(this)
            .setTitle("알림")
            .setMessage("업로드 완료")
            .setPositiveButton("확인") {
                    dialog, which ->
                Toast.makeText(applicationContext, "업로드완료", Toast.LENGTH_SHORT).show()
                when(flag){
                    0 -> startActivity(Intent(this, ContentsIndexActivity::class.java))
                    1 -> startActivity(Intent(this, ReportsIndexActivity::class.java))
                }

            }
            .create()
        alert.show()
    }


}