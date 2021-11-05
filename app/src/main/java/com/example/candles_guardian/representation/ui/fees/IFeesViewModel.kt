package com.example.candles_guardian.representation.ui.fees

import com.example.candles_guardian.pojo.Fees
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

interface IFeesViewModel {
    fun setAtrribute(mApiHelper: ApiHelper)

    suspend fun getFees(username: String): MutableStateFlow<Resource<List<Fees>>>
}