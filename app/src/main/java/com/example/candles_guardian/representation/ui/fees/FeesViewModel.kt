package com.example.candles_guardian.representation.ui.fees

import androidx.lifecycle.ViewModel
import com.example.candles_guardian.Repository.Repo
import com.example.candles_guardian.pojo.Fees
import com.example.candles_guardian.pojo.StuBehaviourNote
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

class FeesViewModel : ViewModel(), IFeesViewModel {
    private var repo: Repo
    private var mApiHelper: ApiHelper? = null
    var feesResponse: MutableStateFlow<Resource<List<Fees>>>


    init {
        repo = Repo()
        feesResponse = MutableStateFlow(Resource.loading(null))

    }

    override fun setAtrribute(mApiHelper: ApiHelper) {
        this.mApiHelper = mApiHelper
        repo.setAtrribute(mApiHelper)
    }


    override suspend fun getFees(username: String): MutableStateFlow<Resource<List<Fees>>> {
        feesResponse = repo.getFees(username)

        return feesResponse
    }
}