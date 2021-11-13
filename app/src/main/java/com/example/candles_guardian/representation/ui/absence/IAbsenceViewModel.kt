package com.example.candles_guardian.representation.ui.absence

import com.example.candles_guardian.pojo.Absence
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

interface IAbsenceViewModel {
    fun setAtrribute(mApiHelper: ApiHelper)

    suspend fun getAbsence(userName: String): MutableStateFlow<Resource<List<Absence>>>
}