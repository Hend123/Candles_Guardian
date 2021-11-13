package com.example.candles_guardian.representation.ui.notifications.work_manager


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
import androidx.work.WorkerParameters
import com.example.candles_guardian.pojo.QuizResult
import com.example.candles_guardian.representation.ui.notifications.NotificationHelper
import com.example.candles_guardian.representation.ui.notifications.NotificationReceiver
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.remote.ApiHelperImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*


class FetchDataAndSendNotificationQuizesResultWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    private var mApiHelper: ApiHelperImpl? = null
    private lateinit var quizesResultRespone: MutableStateFlow<List<QuizResult>>
    private var studentId: String? = null
    private var batchNo: String? = null
    private val mCtx = context
    private  lateinit var mPreferences: SharedPreferences
    private lateinit var mPrefsEditor: SharedPreferences.Editor



    fun init() {
        quizesResultRespone = MutableStateFlow(listOf<QuizResult>())
        mPreferences = mCtx.getSharedPreferences("prefs", AppCompatActivity.MODE_PRIVATE)
        mPrefsEditor = mPreferences.edit()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override suspend fun doWork(): Result {
        mApiHelper = ApiHelperImpl(RetrofitClient.getApiService())
        studentId = inputData.getString("studentId")
        batchNo = inputData.getString("batchNo")
        init()
        getQuizesResult(studentId!!,batchNo!!)
        return Result.success()
    }
    fun getQuizesResult(
        studentId: String,
        batchNumber: String
    ) {
        GlobalScope.launch {
            Dispatchers.IO
            try {
                val response = mApiHelper!!.getQuizResult(studentId, batchNumber)
                withContext(Dispatchers.Main) {
                    Log.v("quizesResultWorker", response.toString())
                    for (item in response.indices) {
                        setNotification(
                            response[item].type_Name + " فى "  + response[item].subjects_Name,
                        "فى تاريخ "  + response[item].quiz_Date  ,
                          "عدد الاجابات الصحيحة: " +   response[item].right_Count_Answer + " " +
                                  "عدد الاجابات الخاطئة: " +  response[item].errors_Count_Question + " " +
                                  "نجح" +  response[item].success + " الدرجة: " +  response[item].student_Degree
                        )
                    }

                }

            } catch (e: Exception) {
                Log.e("quizesResultWorkerError", e.toString())
            }
        }
    }


    fun setNotification(
        subjectName: String,
        date: String,
        details:String
    ) {
        val intent = Intent(mCtx, NotificationReceiver::class.java)
        intent.putExtra("notificationType", "quizesResult")
        intent.putExtra("subjectName", subjectName)
        intent.putExtra("details", details)
        intent.putExtra("date", date)
        val requestCode = mPreferences.getInt("requestCodeWMQR", 0)+1
        mPrefsEditor.putInt("requestCodeWMQR", requestCode)
        mPrefsEditor.commit()
        val notificationHelper =
            NotificationHelper(mCtx,intent)
        val nb: NotificationCompat.Builder =
            notificationHelper.channelNotification()
        notificationHelper.manager?.notify(requestCode, nb.build())
    }






}