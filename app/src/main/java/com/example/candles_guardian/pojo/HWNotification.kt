package com.example.candles_guardian.pojo

import com.google.gson.annotations.SerializedName

data class HWNotification(
    @SerializedName("Class_Name") val class_Name: String,
    @SerializedName("Class_Room_Name") val class_Room_Name: String,
    @SerializedName("Branch_Name") val branch_Name: String,
    @SerializedName("Subjects_Name") val subjects_Name: String,
    @SerializedName("User_Full_Name_Ar") val user_Full_Name_Ar: String,
    @SerializedName("Type_Name") val type_Name: String,
    @SerializedName("Exam_Type") val exam_Type: String,
    @SerializedName("Origin_Date") val origin_Date: String,
    @SerializedName("Status_Date") val status_Date: String,
    @SerializedName("Start_Date") val start_Date: String,
    @SerializedName("Expires_Date") val expires_Date: String,
    @SerializedName("From_Time") val from_Time: String,
    @SerializedName("To_Time") val to_Time: String
)
