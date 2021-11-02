package com.example.candles_guardian.representation.ui.main_activity

import androidx.lifecycle.ViewModel
import com.example.candles_guardian.Repository.Repo
import com.example.candles_guardian.pojo.User
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivityViewModel() : ViewModel(), IMainActivityViewModel {
    private var repo: Repo
    private var mApiHelper: ApiHelper? = null
    var loginUserResponse: MutableStateFlow<Resource<User>>

    init {
        repo = Repo()
        loginUserResponse = MutableStateFlow<Resource<User>>(Resource.loading(null))
    }
    override fun setAtrribute(mApiHelper: ApiHelper){
        this.mApiHelper = mApiHelper
        repo.setAtrribute(mApiHelper)
    }
    override suspend fun loginUser(userName:String, password:String):MutableStateFlow<Resource<User>> {
        loginUserResponse = repo.loginUser(userName,password)
       return loginUserResponse
    }
}