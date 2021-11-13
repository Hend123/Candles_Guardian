package com.example.candles_guardian.pojo

import com.google.gson.annotations.SerializedName

data class Absence(
    @SerializedName("Class_Name") val className: String,
    @SerializedName("ClassRoom") val classRoom: String,
    @SerializedName("Absence_Date") val absenceDate: String,
    @SerializedName("Send_SMS") val sendSms: String,
    @SerializedName("SMS_Mobile_Number") val smsMobileNo: String,
    @SerializedName("SMS_Text") val smsText: String,
    @SerializedName("Excuse") val excuse: Boolean,
    @SerializedName("Remarks") val remarks: String
)
