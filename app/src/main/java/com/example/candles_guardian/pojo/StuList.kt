package com.example.candles_guardian.pojo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Parcelize
data class Stu(
    @SerializedName("ID_Iqamah") val iD_Iqamah: String,
    @SerializedName("Student_Name") val studentName: String,
    @SerializedName("Student_Age") val studentAge: Int,
    @SerializedName("classId") val classId: Int,
    @SerializedName("Class_Name") val className: String,
    @SerializedName("classRoomId") val classRoomId: Int,
    @SerializedName("ClassRoom") val classRoom: String,
    @SerializedName("ImagePath") val imagePath: String
): Parcelable
