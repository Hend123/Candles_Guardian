package com.example.retrofitandcoroutine.data.remote

import com.example.candles_guardian.pojo.*
import retrofit2.http.*


interface ApiService {
    @GET("Users/ValidateUser")
    suspend fun loginUser(
        @Query("UserName") userName: String,
        @Query("password") password: String
    ): User

    @GET("Students/GetChildren")
    suspend fun getChildern(@Query("ParentId") parentId: String): List<Stu>

    @GET("StudentBehaviourNotes/GetBehaviourNotes")
    suspend fun getStuBehaviourNotes(@Query("username") username: String): List<StuBehaviourNote>

    @GET("StudentEducationalNotes/GetEducationalNotes")
    suspend fun getStuEductionalNotes(@Query("username") username: String): List<StuBehaviourNote>

    @GET("StudentFeesTrans/GetStuFeesTrans")
    suspend fun getFees(@Query("username") username: String): List<Fees>

    @GET("QuizResult/GetQuizResults")
    suspend fun getQuizResult(
        @Query("studentId") studentId: String,
        @Query("batchNumber") batchNumber: String
    ): List<QuizResult>

    @GET("HomeWork_Notification/HwNotification")
    suspend fun getHWNotification(
        @Query("classId") classId: String,
        @Query("classRoomId") classRoomId: String,
        @Query("batchNumber") batchNumber: String
    ): List<HWNotification>

    @GET("StudentAbsence/GetUserAbsence")
    suspend fun getAbsence(@Query("username") userName: String): List<Absence>

}