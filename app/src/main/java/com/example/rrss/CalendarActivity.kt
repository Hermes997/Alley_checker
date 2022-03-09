package com.example.rrss

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.*
import android.os.Bundle
import android.provider.Settings
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.rrss.databinding.ActivityCalendarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.*


class CalendarActivity : AppCompatActivity() {



    private val binding by lazy { ActivityCalendarBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            finish()
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0f, locationListener())
        }



    }


    internal inner class locationListener : LocationListener{
        override fun onLocationChanged(p0: Location) {
            val geocoder : Geocoder = Geocoder(this@CalendarActivity, Locale.KOREAN)
            var list = geocoder.getFromLocation(35.89949, 128.60832,1) as List<Address>
            println(p0.latitude)


            val scope = CoroutineScope(Dispatchers.Main)

            //binding.textViewC1.text = "위도" + p0.latitude
            //binding.textViewC2.text = "경도" + p0.longitude
            //binding.textViewC3.text = list[0].getAddressLine(0)

            var list1 = geocoder.getFromLocation(35.89500, 128.60780,1) as List<Address>
            when(Region.valueOf(list1[0].thoroughfare).ordinal){
                0 -> binding.textViewC4.text = "고성동\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)"
                1 -> binding.textViewC4.text = "칠성동\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)"
                2 -> binding.textViewC4.text = "침산1동\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)"
                3 -> binding.textViewC4.text = "침산2동\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)"
                4 -> binding.textViewC4.text = "침산3동\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)"
                5 -> binding.textViewC4.text = "노원동\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)"
                6 -> binding.textViewC4.text = "산격1동\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : (아침6시 ~ 오후3시)"
                7 -> binding.textViewC4.text = "산격2동\n배출일(배출시간) : 월,수,금(밤8시~새벽2시)\n" + "수거일(수거시간) : 화,목,토(아침6시 ~ 오후3시)"
                8 -> {
                    binding.textViewC4.text = "산격3동\n배출일(배출시간) : 월,수,금(밤8시~새벽2시)\n" + "수거일(수거시간) : 화,목,토(아침6시 ~ 오후3시)"


                }
                9 -> binding.textViewC4.text = "산격4동\n배출일(배출시간) : 월,수,금(밤8시~새벽2시)\n" + "수거일(수거시간) : 화,목,토(아침6시 ~ 오후3시)"
                10 -> binding.textViewC4.text = "복현1동\n배출일(배출시간) : 월,수,금(밤8시~새벽2시)\n수거일(수거시간) : 화,목,토(아침6시 ~ 오후3시)"
                11 -> binding.textViewC4.text = "복현2동\n배출일(배출시간) : 월,수,금(밤8시~새벽2시)\n수거일(수거시간) : 화,목,토(아침6시 ~ 오후3시)"
                12 -> binding.textViewC4.text = "대현동\n배출일(배출시간) : 월,수,금(밤8시~새벽2시)\n수거일(수거시간) : 화,목,토(아침6시 ~ 오후3시)"
                13 -> binding.textViewC4.text = "검단동\n배출일(배출시간) : 월,수,금(밤8시~새벽2시)\n수거일(수거시간) : 화,목,토(아침6시 ~ 오후3시)"
                14 -> binding.textViewC4.text = "관문동\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)"
                15 -> binding.textViewC4.text = "태전1동\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)"
                16 -> binding.textViewC4.text = "태전2동\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)"
                17 -> binding.textViewC4.text = "학정동\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)"
                18 -> binding.textViewC4.text = "국우동\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)"
                19 -> binding.textViewC4.text = "관음동(관음중앙로 서편)\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)" +
                        "\n관음동(관음중앙로 동편)\n배출일(배출시간) : 월,수,금(밤8시~새벽2시)\n수거일(수거시간) : 화,목,토(아침6시 ~ 오후3시)"
                20 -> binding.textViewC4.text = "무태조야동(공동주택)\n배출일(배출시간) : 화,목,토(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)" +
                        "\n무태조야동(단독주택)\n배출일(배출시간) : 월,수,금(밤8시~새벽2시)\n" + "수거일(수거시간) : 화,목,토(아침6시 ~ 오후3시)"
                21 -> binding.textViewC4.text = "구암동\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)"
                22 -> binding.textViewC4.text = "읍내동\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)"
                23 -> binding.textViewC4.text = "동천동\n배출일(배출시간) : 일,화,목(밤8시~새벽2시)\n수거일(수거시간) : 월,수,금(아침6시 ~ 오후3시)"
            }
        }
    }
}
