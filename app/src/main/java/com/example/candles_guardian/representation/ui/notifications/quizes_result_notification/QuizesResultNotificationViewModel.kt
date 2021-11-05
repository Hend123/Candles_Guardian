package com.example.candles_guardian.representation.ui.notifications.quizes_result_notification

import androidx.lifecycle.ViewModel
import com.example.candles_guardian.Repository.Repo
import com.example.candles_guardian.pojo.HWNotification
import com.example.candles_guardian.pojo.QuizResult
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

class QuizesResultNotificationViewModel : ViewModel(), IQuizesResultNotificationViewModel {
    private var repo: Repo
    private var mApiHelper: ApiHelper? = null
    var quizResultNotificationResponse: MutableStateFlow<Resource<List<QuizResult>>>
    init {
        repo = Repo()
        quizResultNotificationResponse = MutableStateFlow(Resource.loading(null))
    }
     override fun setAtrribute(mApiHelper: ApiHelper) {
        this.mApiHelper = mApiHelper
        repo.setAtrribute(mApiHelper)
    }
     override suspend fun getQuizResult(
         studentId: String,
         batchNumber: String)
         : MutableStateFlow<Resource<List<QuizResult>>> {
         quizResultNotificationResponse = repo.getQuizResult(studentId,batchNumber)

        return quizResultNotificationResponse
    }
}