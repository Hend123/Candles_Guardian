package com.example.candles_guardian.Repository

import android.util.Log
import com.example.candles_guardian.pojo.*
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Exception

class Repo : IRepo {
    private var mApiHelper: ApiHelper? = null
    private var loginUserResponse: MutableStateFlow<Resource<User>>
    private var childernsResponse: MutableStateFlow<Resource<List<Stu>>>
    private var stuBehaviourNotesResponse: MutableStateFlow<Resource<List<StuBehaviourNote>>>
    private var stuEductionalNotesResponse: MutableStateFlow<Resource<List<StuBehaviourNote>>>
    private var feesResponse: MutableStateFlow<Resource<List<Fees>>>
    private var quizResultResponse: MutableStateFlow<Resource<List<QuizResult>>>
    private var hwNotificationResponse: MutableStateFlow<Resource<List<HWNotification>>>


    //    constructor(apiHelper: ApiHelper) : this() {
//        this.mApiHelper = apiHelper
//    }
    init {
        loginUserResponse = MutableStateFlow<Resource<User>>(Resource.loading(null))
        childernsResponse = MutableStateFlow<Resource<List<Stu>>>(Resource.loading(null))
        stuBehaviourNotesResponse =
            MutableStateFlow<Resource<List<StuBehaviourNote>>>(Resource.loading(null))
        stuEductionalNotesResponse =
            MutableStateFlow<Resource<List<StuBehaviourNote>>>(Resource.loading(null))
        feesResponse = MutableStateFlow(Resource.loading(null))
        quizResultResponse = MutableStateFlow(Resource.loading(null))
        hwNotificationResponse = MutableStateFlow(Resource.loading(null))

    }

    override fun setAtrribute(mApiHelper: ApiHelper) {
        this.mApiHelper = mApiHelper
    }

    override suspend fun loginUser(
        userName: String,
        password: String
    ): MutableStateFlow<Resource<User>> {
        GlobalScope.launch(Dispatchers.IO) {
            loginUserResponse.emit(Resource.loading(null))
            try {
                val response = mApiHelper!!.loginUser(userName, password)
                withContext(Dispatchers.Main) {
                    loginUserResponse.emit(Resource.success(response))
                    Log.v("loginUserResponse", loginUserResponse.value.toString())
                }
            } catch (e: Exception) {
                Log.e("loginError", e.toString())
                loginUserResponse.emit(Resource.error(e.toString(), null))
            }

        }
        return loginUserResponse
    }

    override suspend fun getChildern(parentId: String): MutableStateFlow<Resource<List<Stu>>> {
        GlobalScope.launch {
            Dispatchers.IO
            childernsResponse.emit(Resource.loading(null))
            try {
                val response = mApiHelper!!.getChildern(parentId)
                withContext(Dispatchers.Main) {
                    childernsResponse.emit((Resource.success(response)))
                    Log.v("lchildernsResponse", childernsResponse.value.toString())
                }

            } catch (e: Exception) {
                Log.e("childernError", e.toString())
                childernsResponse.emit(Resource.error(e.toString(), null))
            }
        }
        return childernsResponse
    }

    override suspend fun getStuBehaviourNotes(username: String): MutableStateFlow<Resource<List<StuBehaviourNote>>> {
        GlobalScope.launch {
            Dispatchers.IO
            stuBehaviourNotesResponse.emit(Resource.loading(null))
            try {
                val response = mApiHelper!!.getStuBehaviourNotes(username)
                withContext(Dispatchers.Main) {
                    stuBehaviourNotesResponse.emit((Resource.success(response)))
                    Log.v("stuBehaviourNotes", stuBehaviourNotesResponse.value.toString())
                }

            } catch (e: Exception) {
                Log.e("stuBehaviourNotes", e.toString())
                stuBehaviourNotesResponse.emit(Resource.error(e.toString(), null))
            }
        }
        return stuBehaviourNotesResponse
    }

    override suspend fun getStuEductionalNotes(username: String): MutableStateFlow<Resource<List<StuBehaviourNote>>> {
        GlobalScope.launch {
            Dispatchers.IO
            stuEductionalNotesResponse.emit(Resource.loading(null))
            try {
                val response = mApiHelper!!.getStuEductionalNotes(username)
                withContext(Dispatchers.Main) {
                    stuEductionalNotesResponse.emit((Resource.success(response)))
                    Log.v("stuEductionalNotes", stuEductionalNotesResponse.value.toString() + " kk")
                }

            } catch (e: Exception) {
                Log.e("stuEductionalNotes", e.toString())
                stuEductionalNotesResponse.emit(Resource.error(e.toString(), null))
            }
        }
        return stuEductionalNotesResponse
    }

    override suspend fun getFees(username: String): MutableStateFlow<Resource<List<Fees>>> {
        GlobalScope.launch {
            Dispatchers.IO
            feesResponse.emit(Resource.loading(null))
            try {
                val response = mApiHelper!!.getFees(username)
                withContext(Dispatchers.Main) {
                    feesResponse.emit((Resource.success(response)))
                    Log.v("feesResponse", feesResponse.value.toString())
                }

            } catch (e: Exception) {
                Log.e("feesResponse", e.toString())
                feesResponse.emit(Resource.error(e.toString(), null))
            }
        }
        return feesResponse
    }

    override suspend fun getQuizResult(
        studentId: String,
        batchNumber: String
    ): MutableStateFlow<Resource<List<QuizResult>>> {
        GlobalScope.launch {
            Dispatchers.IO
            quizResultResponse.emit(Resource.loading(null))
            try {
                val response = mApiHelper!!.getQuizResult(studentId, batchNumber)
                withContext(Dispatchers.Main) {
                    quizResultResponse.emit((Resource.success(response)))
                    Log.v("quizResultResponse", quizResultResponse.value.toString())
                }

            } catch (e: Exception) {
                Log.e("quizResultResponse", e.toString())
                quizResultResponse.emit(Resource.error(e.toString(), null))
            }
        }
        return quizResultResponse
    }

    override suspend fun getHWNotification(
        classId: String,
        classRoomId: String,
        batchNumber: String
    ): MutableStateFlow<Resource<List<HWNotification>>> {
        GlobalScope.launch {
            Dispatchers.IO
            hwNotificationResponse.emit(Resource.loading(null))
            try {
                val response = mApiHelper!!.getHWNotification(classId, classRoomId, batchNumber)
                withContext(Dispatchers.Main) {
                    hwNotificationResponse.emit((Resource.success(response)))
                    Log.v("hwNotificationResponse", hwNotificationResponse.value.toString())
                }

            } catch (e: Exception) {
                Log.e("hwNotificationError", e.toString())
                hwNotificationResponse.emit(Resource.error(e.toString(), null))
            }
        }
        return hwNotificationResponse
    }

}