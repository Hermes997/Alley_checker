package com.example.rrss

import java.util.*

data class WriteInfo(
    val title : String? = null,
    val contents : String? = null,
    val publisher : String? = null,
    val date : String? = null,
    val date1 : String? = null,
    var imageUri: ArrayList<String>? = null,
    val uid : String? = null
)