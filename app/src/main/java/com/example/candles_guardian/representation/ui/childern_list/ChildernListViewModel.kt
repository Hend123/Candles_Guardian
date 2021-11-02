package com.example.candles_guardian.representation.ui.childern_list


import androidx.lifecycle.ViewModel
import com.example.candles_guardian.Repository.Repo
import com.example.candles_guardian.pojo.Stu
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

class ChildernListViewModel : ViewModel(), IChildernListViewModel {
    private  var repo:Repo
    private var mApiHelper: ApiHelper? = null

    var childernsResponse: MutableStateFlow<Resource<List<Stu>>>

    init {
        repo = Repo()
        childernsResponse = MutableStateFlow<Resource<List<Stu>>>(Resource.loading(null))
    }
    override fun setAtrribute(mApiHelper: ApiHelper){
        this.mApiHelper = mApiHelper
        repo.setAtrribute(mApiHelper)
    }
    override suspend fun getChildern(parentId: String): MutableStateFlow<Resource<List<Stu>>> {
        childernsResponse = repo.getChildern(parentId)
        return childernsResponse
    }

}