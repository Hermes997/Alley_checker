package com.example.rrss

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rrss.databinding.ActivityHowBinding

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.rrss.databinding.ActivityMapsBinding
import com.example.rrss.databinding.MarkerlayoutxmlBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val binding1 by lazy {MarkerlayoutxmlBinding.inflate(layoutInflater)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled= true



        val trashcan1 = LatLng(35.89440, 128.60596)
        val trashcan2 = LatLng(35.89906, 128.60699)
        val trashcan3 = LatLng(35.89475, 128.60866)

        mMap.addMarker(MarkerOptions().position(trashcan1).title("산격동#1"))
        mMap.addMarker(MarkerOptions().position(trashcan2).title("산격동#2"))
        mMap.addMarker(MarkerOptions().position(trashcan3).title("산격동#3"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trashcan1, 15.0F))



    }




}