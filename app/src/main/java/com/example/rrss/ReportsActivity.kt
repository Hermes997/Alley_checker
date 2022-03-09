package com.example.rrss

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.rrss.databinding.ActivityReportsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

class ReportsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityReportsBinding.inflate(layoutInflater)}
    private val storage = Firebase.storage
    private var imageList = ArrayList<Uri>()
    private val comentList = ArrayList<ComentInfo>()
    private var docPath : String? = null
    private var viewPageradapter = ViewPagerAdapter(imageList)
    private var user : FirebaseUser? =  FirebaseAuth.getInstance().currentUser

    private var db = FirebaseFirestore.getInstance()
    private var title : String? = null
    private var contents : String? = null
    private var publisher : String? = null
    private var date : String? = null
    private var date1 : String? = null
    private var size : Int = 0
    private var uid : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (intent.hasExtra("title")) {
            title = intent.getStringExtra("title")
        }

        if (intent.hasExtra("contents")) {
            contents = intent.getStringExtra("contents")
        }

        if (intent.hasExtra("publisher")) {
            publisher = intent.getStringExtra("publisher")
        }

        if (intent.hasExtra("date")) {
            date = intent.getStringExtra("date")
        }

        if (intent.hasExtra("date1")) {
            date1 = intent.getStringExtra("date1")
        }

        if (intent.hasExtra("size")) {
            size = intent.getIntExtra("size", 0)
        }

        if (intent.hasExtra("uid")) {
            uid = intent.getStringExtra("uid")
        }

        val profileImage = Firebase.storage.getReference("profileImages/${uid}").downloadUrl





        binding.textViewTk1.text = publisher
        binding.textViewTk2.text = title
        binding.textViewTk3.text = contents
        binding.textViewTk4.text = date

        profileImage.addOnSuccessListener {
            Glide.with(binding.root).load(it).into(binding.imageViewTk1)
        }.addOnFailureListener {  }


        if (size != 0) {
            var count = 0
            for (i in 0 until size) {
                val image = storage.getReference("reportsImages/${date1.toString()}/${i}").downloadUrl
                image.addOnSuccessListener {
                    imageList.add(it)
                    count++
                    if(count == size-1)
                        viewPageradapter.notifyDataSetChanged()

                }.addOnFailureListener { }
            }

        }

        binding.viewPagerTk1.adapter = viewPageradapter

        binding.buttonTk2.setOnClickListener {
            deleteAlert()
        }

        binding.buttonTk3.setOnClickListener {
            comentUpload()
        }

        binding.swipeTk1.setOnRefreshListener {
            represent()
            binding.swipeTk1.isRefreshing = false
        }

        represent()


    }

    private fun deleteAlert(){

        if (intent.hasExtra("docPath")) {
            docPath = intent.getStringExtra("docPath")
        }

        val alert = AlertDialog.Builder(this)
            .setTitle("경고")
            .setMessage("해당 게시글을 삭제합니다")
            .setPositiveButton("삭제") {
                    dialog, which ->
                if(user?.uid.toString() == uid.toString()){
                    val db = FirebaseFirestore.getInstance()

                    db.collection("reports").document(docPath!!)
                        .delete()
                        .addOnSuccessListener {
                            Toast.makeText(applicationContext, "삭제완료", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, ReportsIndexActivity::class.java))
                        }
                        .addOnFailureListener {
                            Toast.makeText(applicationContext, "시스템오류로인한 삭제실패", Toast.LENGTH_SHORT).show()
                        }

                }
                else{
                    Toast.makeText(applicationContext, "권한이 없습니다", Toast.LENGTH_SHORT).show()
                }


            }
            .setNegativeButton("취소"){
                    dialog, which ->
                Toast.makeText(applicationContext, "취소되었습니다.", Toast.LENGTH_SHORT).show()
            }
            .create()
        alert.show()
    }


    private fun updateAlert(){
        val alert = AlertDialog.Builder(this)
            .setTitle("알림")
            .setMessage("해당 게시글을 수정하시겠습니까?")
            .setPositiveButton("수정") {
                    dialog, which ->
                if(user?.uid.toString() == uid.toString()){
                    val db = FirebaseFirestore.getInstance()

                    db.collection("reports").document(docPath!!)
                        .delete()
                        .addOnSuccessListener {
                            //Toast.makeText(applicationContext, "삭제완료", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, ReportsIndexActivity::class.java))
                        }
                        .addOnFailureListener {
                            Toast.makeText(applicationContext, "시스템오류로인한 접근실패", Toast.LENGTH_SHORT).show()
                        }

                }
                else{
                    Toast.makeText(applicationContext, "권한이 없습니다", Toast.LENGTH_SHORT).show()
                }


            }
            .setNegativeButton("취소"){
                    dialog, which ->
                Toast.makeText(applicationContext, "취소되었습니다.", Toast.LENGTH_SHORT).show()
            }
            .create()
        alert.show()
    }



    private fun comentUpload() {

        val finalContent: String = binding.editTextTk1.text.toString()
        user = FirebaseAuth.getInstance().currentUser
        val finalPublisher : String = user?.displayName.toString()
        val long_now = System.currentTimeMillis()
        val t_date = Date(long_now)
        val t_dataFormat = SimpleDateFormat("yy-MM-dd hh:mm:ss E", Locale("ko", "KR"))
        val t_dataFormat1 = SimpleDateFormat("yyMMddhhmmsssss", Locale("ko", "KR"))
        val str_date = t_dataFormat.format(t_date)
        val str_date1 = t_dataFormat1.format(t_date)
        val uid = user?.uid.toString()


        var comentInfo = ComentInfo(finalContent, finalPublisher, str_date, str_date1, uid)
        uploader(comentInfo)

    }

    private fun uploader(comentInfo: ComentInfo){

        if (intent.hasExtra("docPath")) {
            docPath = intent.getStringExtra("docPath")
        }

        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("reports").document(docPath!!).collection("coments")
            .add(comentInfo)
            .addOnSuccessListener {
                confirm()
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }

    }

    private fun confirm(){
        val alert = AlertDialog.Builder(this)
            .setTitle("알림")
            .setMessage("업로드 완료")
            .setPositiveButton("확인") {
                    dialog, which ->
                Toast.makeText(applicationContext, "작성완료", Toast.LENGTH_SHORT).show()
            }
            .create()
        alert.show()
    }

    private fun represent(){

        if (intent.hasExtra("docPath")) {
            docPath = intent.getStringExtra("docPath")
        }

        comentList.clear()
        val blindError = ComentAdapter(comentList, docPath)
        db.collection("reports").document(docPath!!)
            .collection("coments").orderBy("date", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    docPath = document.id
                    val item = ComentInfo(
                        document["contents"] as String,
                        document["publisher"] as String,
                        document["date"] as String,
                        document["date1"] as String,
                        document["uid"] as String)
                    comentList.add(item)
                }
                val adapter = ComentAdapter(comentList, docPath)
                binding.recyclerViewTk1.adapter = adapter
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
            }

        binding.recyclerViewTk1.adapter = blindError
    }
}
