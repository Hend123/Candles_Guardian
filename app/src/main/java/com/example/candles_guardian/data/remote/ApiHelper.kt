package com.example.weatherforecast.data.remote

import com.example.candles_guardian.pojo.*
import retrofit2.http.Field
import retrofit2.http.Query


interface ApiHelper {
    suspend fun loginUser(userName: String, password: String): User
    suspend fun getChildern(parentId: String): List<Stu>
    suspend fun getStuBehaviourNotes(username: String): List<StuBehaviourNote>
    suspend fun getStuEductionalNotes(username: String): List<StuBehaviourNote>
    suspend fun getFees(username: String): List<Fees>
    suspend fun getQuizResult(
        studentId: String,
        batchNumber: String
    ): List<QuizResult>

    suspend fun getHWNotification(
        classId: String,
        classRoomId: String,
        batchNumber: String
    ): List<HWNotification>


}