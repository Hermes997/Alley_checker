package com.example.rrss

import android.content.Intent
import android.service.autofill.OnClickAction
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rrss.databinding.SharetipsviewholderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class RecyclerViewAdapter(private val itemList: ArrayList<WriteInfo>, private val docPathList : ArrayList<String>, private val flag : Int)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(private val binding: SharetipsviewholderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(writeInfo : WriteInfo, docPath: String?, flag : Int) {

            val user : FirebaseUser? =  FirebaseAuth.getInstance().currentUser



            val title = writeInfo.title
            val contents = writeInfo.contents
            val publisher = writeInfo.publisher
            val date = writeInfo.date
            val date1 = writeInfo.date1
            val image = writeInfo.imageUri
            val size = image?.size
            val uid = writeInfo.uid
            val profileImage = Firebase.storage.getReference("profileImages/${uid}").downloadUrl

            binding.sharetipsViewholder1.text = title
            binding.sharetipsViewholder2.text = publisher
            binding.sharetipsViewholder3.text = date
            binding.sharetipsViewholder4.text = contents

            profileImage.addOnSuccessListener {
                Glide.with(binding.root).load(it).into(binding.sharetipsViewholder5)
            }.addOnFailureListener {  }

            when(flag){
                0 -> {
                    if(size != 0){
                        val postsImage = Firebase.storage.getReference("postsImages/${writeInfo.date1.toString()}/0").downloadUrl
                        postsImage.addOnSuccessListener {
                            Glide.with(binding.root).load(it).into(binding.sharetipsViewholder6)
                        }.addOnFailureListener {  }
                    }

                }

                1 -> {

                    if(size != 0){
                        val reportsImage = Firebase.storage.getReference("reportsImages/${writeInfo.date1.toString()}/0").downloadUrl
                        reportsImage.addOnSuccessListener {
                            Glide.with(binding.root).load(it).into(binding.sharetipsViewholder6)
                        }.addOnFailureListener {  }
                    }
                }

            }





            binding.root.setOnClickListener {


                when(flag){

                    0 -> {
                        val intent = Intent(it.context, ContentsActivity::class.java)
                        intent.putExtra("title", title)
                        intent.putExtra("contents", contents)
                        intent.putExtra("publisher", publisher)
                        intent.putExtra("date", date)
                        intent.putExtra("date1", date1)
                        intent.putExtra("image", image)
                        intent.putExtra("size", size)
                        intent.putExtra("uid", uid)
                        intent.putExtra("docPath", docPath)
                        intent.putExtra("Flag", flag)
                        it.context.startActivity(intent)
                    }

                    1 -> {
                        val intent = Intent(it.context, ReportsActivity::class.java)
                        intent.putExtra("title", title)
                        intent.putExtra("contents", contents)
                        intent.putExtra("publisher", publisher)
                        intent.putExtra("date", date)
                        intent.putExtra("date1", date1)
                        intent.putExtra("image", image)
                        intent.putExtra("size", size)
                        intent.putExtra("uid", uid)
                        intent.putExtra("docPath", docPath)
                        intent.putExtra("Flag", flag)
                        it.context.startActivity(intent)
                    }
                }







            }


        }



    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = SharetipsviewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position], docPathList[position], flag)

    }


    override fun getItemCount(): Int {
        return itemList.size
    }



}