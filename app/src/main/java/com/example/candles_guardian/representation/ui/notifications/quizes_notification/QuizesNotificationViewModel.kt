package com.example.candles_guardian.representation.ui.notifications.quizes_notification

import androidx.lifecycle.ViewModel
import com.example.candles_guardian.Repository.Repo
import com.example.candles_guardian.pojo.HWNotification
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

class QuizesNotificationViewModel : ViewModel(), IQuizesNotificationViewModel {
    private var repo: Repo
    private var mApiHelper: ApiHelper? = null
    var hwNotificationResponse: MutableStateFlow<Resource<List<HWNotification>>>
    init {
        repo = Repo()
        hwNotificationResponse = MutableStateFlow(Resource.loading(null))
    }
     override fun setAtrribute(mApiHelper: ApiHelper) {
        this.mApiHelper = mApiHelper
        repo.setAtrribute(mApiHelper)
    }
     override suspend fun getHWNotification(
        classId: String,
        classRoomId: String,
        batchNumber: String
    ): MutableStateFlow<Resource<List<HWNotification>>> {
        hwNotificationResponse = repo.getHWNotification(classId,classRoomId,batchNumber)

        return hwNotificationResponse
    }

}