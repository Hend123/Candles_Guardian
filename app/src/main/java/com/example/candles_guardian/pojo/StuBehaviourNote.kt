package com.example.candles_guardian.pojo

import com.google.gson.annotations.SerializedName

data class StuBehaviourNote(
    @SerializedName("Trans_ID") val trans_ID : Int,
    @SerializedName("Trans_Date") val trans_Date : String,
    @SerializedName("Stu_Notes") val stu_Notes : String,
    @SerializedName("Notes_Type") val notes_Type : Int,
    @SerializedName("Batch_No") val batch_No : Int,
    @SerializedName("Managment_Approve") val managment_Approve : Boolean,
    @SerializedName("Student_Name") val student_Name : String,
    @SerializedName("ID_Iqamah") val iD_Iqamah : Int,
    @SerializedName("Subjects_Name") val subjects_Name : String,
    @SerializedName("Recorded_By") val recorded_By : String,
    @SerializedName("Branch_Name") val branch_Name : String
)
