package com.example.candles_guardian.pojo

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("User_Full_Name_Ar") val userName: String,
    @SerializedName("password") val password: String,
    @SerializedName("School_ID") val schoolID: String,
    @SerializedName("Branch_No") val branchNo: String,
    @SerializedName("image_Path") val imagePath: String,
    @SerializedName("usertype") val usertype: String
)
