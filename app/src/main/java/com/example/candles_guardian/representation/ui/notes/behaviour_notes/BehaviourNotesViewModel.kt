package com.example.candles_guardian.representation.ui.notes.behaviour_notes

import androidx.lifecycle.ViewModel
import com.example.candles_guardian.Repository.Repo
import com.example.candles_guardian.pojo.Fees
import com.example.candles_guardian.pojo.StuBehaviourNote
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

class BehaviourNotesViewModel : ViewModel(), IBehaviourNotesViewModel {
    private var repo: Repo
    private var mApiHelper: ApiHelper? = null

    var stuBehaviourNotesResponse: MutableStateFlow<Resource<List<StuBehaviourNote>>>



    init {
        repo = Repo()
        stuBehaviourNotesResponse =
            MutableStateFlow<Resource<List<StuBehaviourNote>>>(Resource.loading(null))


    }

     override fun setAtrribute(mApiHelper: ApiHelper) {
        this.mApiHelper = mApiHelper
        repo.setAtrribute(mApiHelper)
    }

     override suspend fun getStuBehaviourNotes(username: String): MutableStateFlow<Resource<List<StuBehaviourNote>>> {
        stuBehaviourNotesResponse = repo.getStuBehaviourNotes(username)
        return stuBehaviourNotesResponse
    }




}