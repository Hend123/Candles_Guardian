package com.example.candles_guardian.pojo

import com.google.gson.annotations.SerializedName

data class Fees(
    @SerializedName("batch") val batch : String,
    @SerializedName("branch") val branch : String,
    @SerializedName("Trans_Date") val trans_Date : String,
    @SerializedName("Id_iqama") val id_iqama : Int,
    @SerializedName("Fees_Type") val fees_Type : String,
    @SerializedName("Fees_Amount") val fees_Amount : Int,
    @SerializedName("Vat_Amount") val vat_Amount : Int,
    @SerializedName("Total_Amount") val total_Amount : Int,
    @SerializedName("Discount_Type") val discount_Type : String,
    @SerializedName("Discount_Amount") val discount_Amount : Int,
    @SerializedName("Total_Net_Amount") val total_Net_Amount : Int,
    @SerializedName("Paid_Amount") val paid_Amount : Int,
    @SerializedName("Balance_Amount") val balance_Amount : Int,
    @SerializedName("Debit_Balance") val debit_Balance : Int,
    @SerializedName("Credit_Balance") val credit_Balance : Int,
    @SerializedName("Fees_Description") val fees_Description : String
)
