package com.example.candles_guardian.representation.ui.details

import com.example.candles_guardian.pojo.Fees
import com.example.candles_guardian.pojo.StuBehaviourNote
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

interface IDetailsViewModel {
    fun setAtrribute(mApiHelper: ApiHelper)
    suspend fun getStuBehaviourNotes(username: String): MutableStateFlow<Resource<List<StuBehaviourNote>>>
    suspend fun getStuEductionalNotes(username: String): MutableStateFlow<Resource<List<StuBehaviourNote>>>
    suspend fun getFees(username: String): MutableStateFlow<Resource<List<Fees>>>


}