package com.example.candles_guardian.Repository

import com.example.candles_guardian.pojo.*
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

interface IRepo {
    fun setAtrribute(mApiHelper: ApiHelper)
    suspend fun loginUser(userName: String, password: String): MutableStateFlow<Resource<User>>
    suspend fun getChildern(parentId: String): MutableStateFlow<Resource<List<Stu>>>
    suspend fun getStuBehaviourNotes(username: String): MutableStateFlow<Resource<List<StuBehaviourNote>>>
    suspend fun getStuEductionalNotes(username: String): MutableStateFlow<Resource<List<StuBehaviourNote>>>
    suspend fun getFees(username: String): MutableStateFlow<Resource<List<Fees>>>
    suspend fun getQuizResult(
        studentId: String,
        batchNumber: String
    ): MutableStateFlow<Resource<List<QuizResult>>>

    suspend fun getHWNotification(
        classId: String,
        classRoomId: String,
        batchNumber: String
    ): MutableStateFlow<Resource<List<HWNotification>>>
    suspend fun getAbsence(userName: String): MutableStateFlow<Resource<List<Absence>>>



}