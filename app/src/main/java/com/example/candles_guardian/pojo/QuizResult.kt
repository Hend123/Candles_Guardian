package com.example.candles_guardian.pojo

import com.google.gson.annotations.SerializedName

data class QuizResult(
    @SerializedName("Subjects_Name") val subjects_Name: String,
    @SerializedName("Right_Count_Answer") val right_Count_Answer: Int,
    @SerializedName("Trans_No_Quiz_Header") val trans_No_Quiz_Header: Int,
    @SerializedName("Errors_Count_Question") val errors_Count_Question: Int,
    @SerializedName("Student_Degree") val student_Degree: Int,
    @SerializedName("Success") val success: Boolean,
    @SerializedName("Quiz_Date") val quiz_Date: String,
    @SerializedName("Type_Name") val type_Name: String,
    @SerializedName("Trans_ID_Upload") val trans_ID_Upload: Int
)
