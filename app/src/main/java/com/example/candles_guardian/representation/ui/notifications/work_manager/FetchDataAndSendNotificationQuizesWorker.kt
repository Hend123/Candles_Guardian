package com.example.candles_guardian.representation.ui.notifications.work_manager

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.candles_guardian.pojo.HWNotification
import com.example.candles_guardian.representation.ui.notifications.NotificationHelper
import com.example.candles_guardian.representation.ui.notifications.NotificationReceiver
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.remote.ApiHelperImpl
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class FetchDataAndSendNotificationQuizesWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    private var mApiHelper: ApiHelperImpl? = null
    private lateinit var quizesRespone: MutableStateFlow<List<HWNotification>>
    private var classId: String? = null
    private var classRoomId: String? = null
    private var batchNo: String? = null
    private val mCtx = context
    private lateinit var alarmManager: AlarmManager
    private  lateinit var mPreferences: SharedPreferences
    private lateinit var mPrefsEditor: SharedPreferences.Editor



    fun init() {
        quizesRespone = MutableStateFlow(listOf<HWNotification>())
        alarmManager = mCtx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mPreferences = mCtx.getSharedPreferences("prefs", AppCompatActivity.MODE_PRIVATE)
        mPrefsEditor = mPreferences.edit()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override suspend fun doWork(): Result {
        mApiHelper = ApiHelperImpl(RetrofitClient.getApiService())
        classId = inputData.getString("classId")
        classRoomId = inputData.getString("classRoomId")
        batchNo = inputData.getString("batchNo")
        init()
        getHWNotification(classId!!, classRoomId!!, batchNo!!)
//        val outputData = Data.Builder()
//            .putString("outPut",quizesRespone.toString())
//            .build()
        return Result.success()
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun getHWNotification(
        classId: String,
        classRoomId: String,
        batchNumber: String
    ) {
        GlobalScope.launch {
            Dispatchers.IO
            try {
                val response = mApiHelper!!.getHWNotification(classId, classRoomId, batchNumber)
               // quizesRespone = response as MutableStateFlow<List<HWNotification>>
              //  Log.v("quizesRespone", quizesRespone.toString())
                withContext(Dispatchers.Main) {
                    Log.v("responseWorker", response.toString())
                    for (item in response.indices) {
                        setNotification(
                            response[item].origin_Date,
                            response[item].class_Name + " الفصل " +
                                    response[item].class_Room_Name,
                           "فى "  + response[item].subjects_Name,
                            response[item].type_Name,
                            "من " + response[item].start_Date + " الى " +
                                    response[item].expires_Date
                        )
                    }

                }

            } catch (e: Exception) {
                Log.e("errorWorker", e.toString())
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun setNotification(
        originData: String,
        className: String,
        subjectName: String,
        typeName: String,
        date: String
    ) {
        val intent = Intent(mCtx, NotificationReceiver::class.java)
        intent.putExtra("className", className)
        intent.putExtra("subjectName", subjectName)
        intent.putExtra("typeName", typeName)
        intent.putExtra("date", date)
        val requestCode = mPreferences.getInt("requestCodeWMQ", 0)+1
        mPrefsEditor.putInt("requestCodeWMQ", requestCode)
        mPrefsEditor.commit()
        val notificationHelper =
            NotificationHelper(mCtx,intent)
        val nb: NotificationCompat.Builder =
            notificationHelper.channelNotification()
        notificationHelper.manager?.notify(requestCode, nb.build())

    }

//    @SuppressLint("SimpleDateFormat")
//    fun dateToSuitableDate(date: String): Long {
//        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//        val outputFormat = SimpleDateFormat("dd-MM-yyyy-HH:mm:ss")
//        val date: Date = inputFormat.parse(date)
//        val formattedDate: String = outputFormat.format(date)
//        Log.v("formattedDate", formattedDate)
//        return dateToTimeStamp(formattedDate)
//    }
//
//    @SuppressLint("SimpleDateFormat")
//    fun dateToTimeStamp(date: String): Long {
//        val date = SimpleDateFormat("dd-MM-yyyy-HH:mm:ss").parse(date)
//        Log.v("date", date.time.toString())
//        return date.time
//    }




}