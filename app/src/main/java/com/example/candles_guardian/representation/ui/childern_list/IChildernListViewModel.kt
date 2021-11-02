package com.example.candles_guardian.representation.ui.childern_list

import com.example.candles_guardian.pojo.Stu
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

interface IChildernListViewModel {
    fun setAtrribute(mApiHelper: ApiHelper)
    suspend fun getChildern(parentId: String): MutableStateFlow<Resource<List<Stu>>>
}