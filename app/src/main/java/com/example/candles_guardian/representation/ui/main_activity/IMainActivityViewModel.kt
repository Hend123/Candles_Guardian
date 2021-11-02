package com.example.candles_guardian.representation.ui.main_activity

import com.example.candles_guardian.pojo.User
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

interface IMainActivityViewModel {
    suspend fun loginUser(userName: String, password: String): MutableStateFlow<Resource<User>>
    fun setAtrribute(mApiHelper: ApiHelper)
}