package com.example.rrss

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.rrss.databinding.ActivityHowBinding


class HowActivity : AppCompatActivity() {

    private val binding by lazy {ActivityHowBinding.inflate(layoutInflater)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var image1 = ImageView(this)
        var image2 = ImageView(this)
        var image3 = ImageView(this)
        var image4 = ImageView(this)
        var image5 = ImageView(this)
        var image6 = ImageView(this)
        var image7 = ImageView(this)
        var image8 = ImageView(this)
        var image9 = ImageView(this)
        var image10 = ImageView(this)
        var image11 = ImageView(this)
        var image12 = ImageView(this)
        var image13 = ImageView(this)
        var image14 = ImageView(this)
        var image15 = ImageView(this)
        var image16 = ImageView(this)


        image1.setImageResource(R.drawable.h1_1)
        image1.adjustViewBounds = true
        binding.linear.addView(image1)
        image2.setImageResource(R.drawable.h1_2)
        image2.adjustViewBounds = true
        binding.linear.addView(image2)
        image3.setImageResource(R.drawable.h1_3)
        image3.adjustViewBounds = true
        binding.linear.addView(image3)
        image4.setImageResource(R.drawable.h1_4)
        image4.adjustViewBounds = true
        binding.linear.addView(image4)
        image5.setImageResource(R.drawable.h1_5)
        image5.adjustViewBounds = true
        binding.linear.addView(image5)
        image6.setImageResource(R.drawable.h1_6)
        image6.adjustViewBounds = true
        binding.linear.addView(image6)
        image7.setImageResource(R.drawable.h1_7)
        image7.adjustViewBounds = true
        binding.linear.addView(image7)
        image8.setImageResource(R.drawable.h2)
        image8.adjustViewBounds = true
        image9.setImageResource(R.drawable.h3)
        image9.adjustViewBounds = true
        image10.setImageResource(R.drawable.h4)
        image10.adjustViewBounds = true
        image11.setImageResource(R.drawable.h5)
        image11.adjustViewBounds = true
        image12.setImageResource(R.drawable.h6_1)
        image12.adjustViewBounds = true
        image13.setImageResource(R.drawable.h6_2)
        image13.adjustViewBounds = true
        image14.setImageResource(R.drawable.h6_3)
        image14.adjustViewBounds = true
        image15.setImageResource(R.drawable.h6_4)
        image15.adjustViewBounds = true
        image16.setImageResource(R.drawable.h6_5)
        image16.adjustViewBounds = true



        binding.buttonH1.setOnClickListener{
            binding.linear.removeAllViews()
            binding.linear.addView(image8)

        }


        binding.buttonH2.setOnClickListener{
            binding.linear.removeAllViews()
            binding.linear.addView(image9)

        }

        binding.buttonH3.setOnClickListener{
            binding.linear.removeAllViews()
            binding.linear.addView(image10)

        }

        binding.buttonH4.setOnClickListener{
            binding.linear.removeAllViews()
            binding.linear.addView(image11)

        }

        binding.buttonH5.setOnClickListener{
            binding.linear.removeAllViews()
            binding.linear.addView(image12)
            binding.linear.addView(image13)
            binding.linear.addView(image14)
            binding.linear.addView(image15)
            binding.linear.addView(image16)

        }

    }
}