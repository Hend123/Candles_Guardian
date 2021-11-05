package com.example.candles_guardian.representation.ui.notifications.quizes_notification

import com.example.candles_guardian.pojo.HWNotification
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

interface IQuizesNotificationViewModel {
    fun setAtrribute(mApiHelper: ApiHelper)

    suspend fun getHWNotification(
        classId: String,
        classRoomId: String,
        batchNumber: String
    ): MutableStateFlow<Resource<List<HWNotification>>>
}