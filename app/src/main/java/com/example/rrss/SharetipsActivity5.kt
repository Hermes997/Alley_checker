package com.example.rrss


import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rrss.databinding.ActivitySharetips5Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class SharetipsActivity5 : AppCompatActivity() {

    private val binding by lazy { ActivitySharetips5Binding.inflate(layoutInflater)}
    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var user : FirebaseUser? =  FirebaseAuth.getInstance().currentUser
    private val storageRef = Firebase.storage.reference
    private var uri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.imageViewTd1.setOnClickListener {
            val tempnickname : String? = binding.editTextTd1.text.toString()
            val tempbirthday : String? = binding.editTextTd2.text.toString()
            val temptelnum : String? = binding.editTextTd3.text.toString()
            val tempaddress : String? = binding.editTextTd4.text.toString()
            var intent = Intent(this, ProfileImageActivity::class.java)
            intent.putExtra("userInfo1", tempnickname)
            intent.putExtra("userInfo2", tempbirthday)
            intent.putExtra("userInfo3", temptelnum)
            intent.putExtra("userInfo4", tempaddress)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        if(intent.hasExtra("imageUri")){
            uri = intent.getParcelableExtra("imageUri")
            binding.imageViewTd1.setImageURI(uri)
        }
        binding.editTextTd1.setText(intent.getStringExtra("userInfo1"))
        binding.editTextTd2.setText(intent.getStringExtra("userInfo2"))
        binding.editTextTd3.setText(intent.getStringExtra("userInfo3"))
        binding.editTextTd4.setText(intent.getStringExtra("userInfo4"))

        binding.buttonTd1.setOnClickListener {
            userdataupload()
            val imageRef = storageRef.child("profileImages/${user?.uid}")
            val uploadTask = imageRef.putFile(uri!!)

            uploadTask.addOnFailureListener{}.addOnCompleteListener{}
            startActivity(Intent(this, MainActivity::class.java))

        }
    }


    private fun userdataupload() {

        val nickname : String? = binding.editTextTd1.text.toString()
        val birthday : String? = binding.editTextTd2.text.toString()
        val telnum : String? = binding.editTextTd3.text.toString()
        val address : String? = binding.editTextTd4.text.toString()
        val userdata = UserInfo(nickname, birthday, telnum, address)

        uploader(userdata)

        val profileUpdates = userProfileChangeRequest {
            displayName = nickname

        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }

    }

    private fun uploader(userdata : UserInfo){
        db.collection("users").document(user?.uid.toString()).set(userdata)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot added with ID: ")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, ContentsIndexActivity::class.java))
    }


}