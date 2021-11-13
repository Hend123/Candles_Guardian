package com.example.candles_guardian.representation.ui.absence

import androidx.lifecycle.ViewModel
import com.example.candles_guardian.Repository.Repo
import com.example.candles_guardian.pojo.Absence
import com.example.candles_guardian.pojo.Fees
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow

class AbsenceViewModel : ViewModel(), IAbsenceViewModel {
    private var repo: Repo
    private var mApiHelper: ApiHelper? = null
    var absenceResponse: MutableStateFlow<Resource<List<Absence>>>


    init {
        repo = Repo()
        absenceResponse = MutableStateFlow(Resource.loading(null))

    }

     override fun setAtrribute(mApiHelper: ApiHelper) {
        this.mApiHelper = mApiHelper
        repo.setAtrribute(mApiHelper)
    }


     override suspend fun getAbsence(userName: String): MutableStateFlow<Resource<List<Absence>>> {
         absenceResponse = repo.getAbsence(userName)

        return absenceResponse
    }
}