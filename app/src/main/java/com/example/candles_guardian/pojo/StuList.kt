package com.example.candles_guardian.pojo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Stu (
  @SerializedName("ID_Iqamah") val iD_Iqamah : String,
    @SerializedName("Student_Name") val studentName : String,
    @SerializedName("Student_Age") val studentAge : Int,
    @SerializedName("Class_Name") val className : String,
    @SerializedName("ClassRoom") val classRoom : String
) : Parcelable {
  constructor(parcel: Parcel) : this(
    parcel.readString()!!,
    parcel.readString()!!,
    parcel.readInt(),
    parcel.readString()!!,
    parcel.readString()!!
  ) {
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(iD_Iqamah)
    parcel.writeString(studentName)
    parcel.writeInt(studentAge)
    parcel.writeString(className)
    parcel.writeString(classRoom)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Stu> {
    override fun createFromParcel(parcel: Parcel): Stu {
      return Stu(parcel)
    }

    override fun newArray(size: Int): Array<Stu?> {
      return arrayOfNulls(size)
    }
  }
}