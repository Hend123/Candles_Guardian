package com.example.candles_guardian.representation.ui.notes.eductional_notes

import com.example.candles_guardian.pojo.StuBehaviourNote
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

interface IEductionalNotesViewModel {
    fun setAtrribute(mApiHelper: ApiHelper)

    suspend fun getStuEductionalNotes(username: String): MutableStateFlow<Resource<List<StuBehaviourNote>>>
}