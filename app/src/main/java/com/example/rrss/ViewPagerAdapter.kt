package com.example.rrss

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rrss.databinding.ActivityAddMemoryBinding
import com.example.rrss.databinding.LayoutImageBinding
import com.example.rrss.databinding.SharetipsviewholderBinding
import java.util.ArrayList

class ViewPagerAdapter(private val itemList: ArrayList<Uri>): RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    class ViewHolder(private val binding: LayoutImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(uri: Uri) {
            Glide.with(binding.root).load(uri).into(binding.imageViewTf1)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = LayoutImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}