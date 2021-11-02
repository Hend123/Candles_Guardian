package com.example.candles_guardian.representation.ui.details

import androidx.lifecycle.ViewModel
import com.example.candles_guardian.Repository.Repo
import com.example.candles_guardian.pojo.Fees
import com.example.candles_guardian.pojo.Stu
import com.example.candles_guardian.pojo.StuBehaviourNote
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

class DetailsViewModel : ViewModel(), IDetailsViewModel {
    private var repo: Repo
    private var mApiHelper: ApiHelper? = null

    var stuBehaviourNotesResponse: MutableStateFlow<Resource<List<StuBehaviourNote>>>
    var stuEductionalNotesResponse: MutableStateFlow<Resource<List<StuBehaviourNote>>>
    var feesResponse: MutableStateFlow<Resource<List<Fees>>>


    init {
        repo = Repo()
        stuBehaviourNotesResponse =
            MutableStateFlow<Resource<List<StuBehaviourNote>>>(Resource.loading(null))
        stuEductionalNotesResponse =
            MutableStateFlow<Resource<List<StuBehaviourNote>>>(Resource.loading(null))
        feesResponse = MutableStateFlow(Resource.loading(null))

    }

    override fun setAtrribute(mApiHelper: ApiHelper) {
        this.mApiHelper = mApiHelper
        repo.setAtrribute(mApiHelper)
    }

    override suspend fun getStuBehaviourNotes(username: String): MutableStateFlow<Resource<List<StuBehaviourNote>>> {
        stuBehaviourNotesResponse = repo.getStuBehaviourNotes(username)
        return stuBehaviourNotesResponse
    }

    override suspend fun getStuEductionalNotes(username: String): MutableStateFlow<Resource<List<StuBehaviourNote>>> {
        stuEductionalNotesResponse = repo.getStuEductionalNotes(username)

        return stuEductionalNotesResponse
    }

    override suspend fun getFees(username: String): MutableStateFlow<Resource<List<Fees>>> {
        feesResponse = repo.getFees(username)

        return feesResponse
    }
}