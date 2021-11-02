package com.example.candles_guardian.pojo

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("classRoom")
fun classRoom(textView: TextView, classRoomTxt: String) {
    textView.text = String.format("الفصل " + classRoomTxt)
}
@BindingAdapter("age")
fun age(textView: TextView, ageTxt: Int) {
    textView.text = String.format(ageTxt.toString() + " عام " )
}