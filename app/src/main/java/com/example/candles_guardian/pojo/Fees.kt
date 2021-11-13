package com.example.candles_guardian.pojo

import com.google.gson.annotations.SerializedName

data class Fees(
    @SerializedName("batch") val batch : String,
    @SerializedName("branch") val branch : String,
    @SerializedName("Trans_Date") val transDate : String,
    @SerializedName("Id_iqama") val idIqama : String,
    @SerializedName("Fees_Type") val feesType : String,
    @SerializedName("Fees_Amount") val feesAmount : Double,
    @SerializedName("Vat_Amount") val vatAmount : Double,
    @SerializedName("Total_Amount") val totalAmount : Double,
    @SerializedName("Discount_Type") val discountType : String,
    @SerializedName("Discount_Amount") val discountAmount : Double,
    @SerializedName("Total_Net_Amount") val totalNet_Amount : Double,
    @SerializedName("Paid_Amount") val paidAmount : Double,
    @SerializedName("Balance_Amount") val balanceAmount : Double,
    @SerializedName("Debit_Balance") val debitBalance : Double,
    @SerializedName("Credit_Balance") val creditBalance : Double,
    @SerializedName("Fees_Description") val feesDescription : String
)
