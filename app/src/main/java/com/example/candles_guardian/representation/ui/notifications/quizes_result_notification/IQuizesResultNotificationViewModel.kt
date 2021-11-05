package com.example.candles_guardian.representation.ui.notifications.quizes_result_notification

import com.example.candles_guardian.pojo.QuizResult
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

interface IQuizesResultNotificationViewModel {
    fun setAtrribute(mApiHelper: ApiHelper)

    suspend fun getQuizResult(
        studentId: String,
        batchNumber: String
    )
            : MutableStateFlow<Resource<List<QuizResult>>>
}