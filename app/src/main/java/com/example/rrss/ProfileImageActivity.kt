package com.example.rrss

import android.app.Activity
import android.content.ClipData
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.rrss.databinding.ActivityProfileImageBinding
import com.example.rrss.databinding.ActivitySharetips5Binding

import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ProfileImageActivity : AppCompatActivity() {

    private val binding by lazy { ActivityProfileImageBinding.inflate(layoutInflater)}
    private val binding1 by lazy { ActivitySharetips5Binding.inflate(layoutInflater)}
    val REQUEST_GALLERY_TAKE = 5
    val REQ_CAMERA_PERMISSION = 3
    private var photoURI : Uri? = null
    private val REQUEST_CREATE_EX = 4
    private var imageList = ArrayList<Uri>()
    private val adapter = ViewPagerAdapter(imageList)

    var tempnickname : String? = null
    var tempbirthday : String? = null
    var temptelnum : String? = null
    var tempaddress : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        selectCamera()
        binding.buttonTh1.setOnClickListener { openGalleryForImage() }
        binding.buttonTh2.setOnClickListener { dispatchTakePictureIntentEx() }

        tempnickname = intent.getStringExtra("userInfo1")
        tempbirthday = intent.getStringExtra("userInfo2")
        temptelnum = intent.getStringExtra("userInfo3")
        tempaddress = intent.getStringExtra("userInfo4")

    }

    override fun onResume() {
        super.onResume()


        binding.buttonTh3.setOnClickListener {
            val intent = Intent(this, SharetipsActivity5::class.java)
            intent.putExtra("imageUri", photoURI)
            intent.putExtra("userInfo1", tempnickname)
            intent.putExtra("userInfo2", tempbirthday)
            intent.putExtra("userInfo3", temptelnum)
            intent.putExtra("userInfo4", tempaddress)
            photoURI = null
            startActivity(intent)
        }
        binding.ViewPagerTh1.adapter = adapter
    }

    private fun selectCamera() {
        var permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        if (permission == PackageManager.PERMISSION_DENIED) {
            // 권한 없어서 요청
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQ_CAMERA_PERMISSION)
        }
        else {
            // 권한 있음

        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_TAKE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if( requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "권한 설정 OK", Toast.LENGTH_SHORT).show() }
        else { Toast.makeText(this, "권한 허용 안됨", Toast.LENGTH_SHORT).show() } }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CREATE_EX) {

                imageList.clear()
                if (photoURI != null) {
                    imageList.add(photoURI!!)
                }
            } else if (requestCode == REQUEST_GALLERY_TAKE) {

                val clipData: ClipData? = data?.clipData
                if (clipData != null) {
                    if (clipData.itemCount > 10) {
                        Toast.makeText(this, "사진은 10개까지 선택가능", Toast.LENGTH_SHORT)
                    } else if (clipData.itemCount == 1) {
                        imageList.clear()
                        imageList.add(clipData.getItemAt(0).uri)
                        photoURI = clipData.getItemAt(0).uri

                    } else if (clipData.itemCount > 1) {
                        imageList.clear()
                        for (i in 0 until clipData.itemCount) {
                            imageList.add(clipData.getItemAt(i).getUri())
                        }
                    }
                }
            }
        }
        ViewPagerAdapter(imageList)
    }
    private fun createImageUri(filename:String, mimeType:String):Uri?{
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME,filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) }



    private fun dispatchTakePictureIntentEx() {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val takePictureIntent : Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val uri : Uri? = createImageUri("JPEG_${timeStamp}_", "image/jpg")
        photoURI = uri
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(takePictureIntent, REQUEST_CREATE_EX) }

    fun loadBitmapFromMediaStoreBy(photoUri: Uri) : Bitmap?{
        var image: Bitmap? = null
        try{
            image = if(Build.VERSION.SDK_INT > 27){
                val source: ImageDecoder.Source = ImageDecoder.createSource(this.contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)
            }
            else{ MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
            }
        }
        catch(e: IOException){
            e.printStackTrace()
        }
        return image
    }

}