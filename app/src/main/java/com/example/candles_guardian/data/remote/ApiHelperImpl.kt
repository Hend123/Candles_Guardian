package com.example.weatherforecast.data.remote

import com.example.candles_guardian.pojo.*
import com.example.retrofitandcoroutine.data.remote.ApiService


class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun loginUser(userName: String, password: String): User =
        apiService.loginUser(userName, password)


    override suspend fun getChildern(parentId: String): List<Stu> = apiService.getChildern(parentId)


    override suspend fun getStuBehaviourNotes(username: String): List<StuBehaviourNote> =
        apiService.getStuBehaviourNotes(username)

    override suspend fun getStuEductionalNotes(username: String): List<StuBehaviourNote> =
        apiService.getStuEductionalNotes(username)

    override suspend fun getFees(username: String): List<Fees> =
        apiService.getFees(username)

    override suspend fun getQuizResult(studentId: String, batchNumber: String): List<QuizResult> =
        apiService.getQuizResult(studentId, batchNumber)

    override suspend fun getHWNotification(
        classId: String,
        classRoomId: String,
        batchNumber: String
    ): List<HWNotification> =
        apiService.getHWNotification(classId, classRoomId, batchNumber)
}