package com.example.candles_guardian.representation.ui.notifications.quizes_result_notification

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.candles_guardian.R
import com.example.candles_guardian.databinding.QuizesNotificationFragmentBinding
import com.example.candles_guardian.databinding.QuizesResultNotificationFragmentBinding
import com.example.candles_guardian.pojo.QuizResult
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.remote.ApiHelperImpl
import com.example.weatherforecast.utils.Resource
import com.example.weatherforecast.utils.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect

class QuizesResultNotificationFragment : Fragment() {
    private var _binding: QuizesResultNotificationFragmentBinding? = null
    private lateinit var viewModel: QuizesResultNotificationViewModel


    companion object {
        fun newInstance() = QuizesResultNotificationFragment()
    }
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QuizesResultNotificationFragmentBinding.inflate(inflater, container, false)
        init()
        lifecycleScope.launchWhenStarted {
            getQuizResult("1128971916","1440")
        }
        return binding.root
    }


    private fun init(){
        viewModel = ViewModelProvider(this).get(QuizesResultNotificationViewModel::class.java)
        viewModel.setAtrribute(ApiHelperImpl(RetrofitClient.getApiService()))

    }
    private suspend fun getQuizResult(
        studentId: String,
        batchNumber: String) {
        viewModel.getQuizResult(studentId, batchNumber)
        viewModel.quizResultNotificationResponse.collect {
            it.let {
                //binding.progressBar.visibility = View.VISIBLE
                when (it.status) {

                    Status.SUCCESS -> {
                        Log.v("status", "success")
                        // binding.progressBar.visibility = View.GONE
                        it.data?.let {

                            Log.v("dataQuizResultNot", it.toString())

                        }
                    }
                    Status.LOADING -> {
                        //binding.progressBar.visibility = View.VISIBLE
                        Log.v("status", "loading")
                    }
                    Status.ERROR -> {
                        // binding.progressBar.visibility = View.GONE
                        Log.v("status", "error " + it.toString())
                        Toast.makeText(
                            context,
                            getString(R.string.errorConnection),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}