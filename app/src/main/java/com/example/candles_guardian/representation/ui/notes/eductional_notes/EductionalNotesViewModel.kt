package com.example.candles_guardian.representation.ui.notes.eductional_notes

import androidx.lifecycle.ViewModel
import com.example.candles_guardian.Repository.Repo
import com.example.candles_guardian.pojo.StuBehaviourNote
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

class EductionalNotesViewModel : ViewModel(), IEductionalNotesViewModel {
    private var repo: Repo
    private var mApiHelper: ApiHelper? = null
    var stuEductionalNotesResponse: MutableStateFlow<Resource<List<StuBehaviourNote>>>


    init {
        repo = Repo()
        stuEductionalNotesResponse =
            MutableStateFlow<Resource<List<StuBehaviourNote>>>(Resource.loading(null))

    }

    override fun setAtrribute(mApiHelper: ApiHelper) {
        this.mApiHelper = mApiHelper
        repo.setAtrribute(mApiHelper)
    }


    override suspend fun getStuEductionalNotes(username: String): MutableStateFlow<Resource<List<StuBehaviourNote>>> {
        stuEductionalNotesResponse = repo.getStuEductionalNotes(username)

        return stuEductionalNotesResponse
    }}