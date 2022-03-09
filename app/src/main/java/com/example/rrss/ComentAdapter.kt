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
import com.example.rrss.databinding.ComentViewholderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class ComentAdapter(private val itemList: ArrayList<ComentInfo>, private val docPath : String?): RecyclerView.Adapter<ComentAdapter.ViewHolder>() {


    class ViewHolder(private val binding: ComentViewholderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comentInfo : ComentInfo, docPath: String?) {

            val contents = comentInfo.contents
            val publisher = comentInfo.publisher
            val date = comentInfo.date
            val date1 = comentInfo.date1
            val uid = comentInfo.uid

            val profileImage = Firebase.storage.getReference("profileImages/${uid}").downloadUrl





            binding.comentContents.text = contents
            binding.comentPublisher.text = publisher
            binding.comentDate.text = date

            profileImage.addOnSuccessListener {
                Glide.with(binding.root).load(it).into(binding.comentProfileimg)
            }.addOnFailureListener {  }



        }



    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = ComentViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position], docPath)

    }


    override fun getItemCount(): Int {
        return itemList.size
    }

}