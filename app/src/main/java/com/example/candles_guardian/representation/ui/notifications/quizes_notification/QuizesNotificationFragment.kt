package com.example.candles_guardian.representation.ui.notifications.quizes_notification

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
import com.example.candles_guardian.databinding.FragmentNotificationsBinding
import com.example.candles_guardian.databinding.QuizesNotificationFragmentBinding
import com.example.candles_guardian.representation.ui.notifications.NotificationsViewModel
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.remote.ApiHelperImpl
import com.example.weatherforecast.utils.Status
import kotlinx.coroutines.flow.collect

class QuizesNotificationFragment : Fragment() {
    private var _binding: QuizesNotificationFragmentBinding? = null
    private lateinit var viewModel: QuizesNotificationViewModel


    companion object {
        fun newInstance() = QuizesNotificationFragment()
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QuizesNotificationFragmentBinding.inflate(inflater, container, false)
        init()

        lifecycleScope.launchWhenStarted {
            getHWNotification("1", "1", "1440")
        }

        return binding.root
    }


    private fun init() {
        viewModel = ViewModelProvider(this).get(QuizesNotificationViewModel::class.java)
        viewModel.setAtrribute(ApiHelperImpl(RetrofitClient.getApiService()))
    }

    private suspend fun getHWNotification(
        classId: String,
        classRoomId: String,
        batchNumber: String
    ) {
        viewModel.getHWNotification(classId, classRoomId, batchNumber)
        viewModel.hwNotificationResponse.collect {
            it.let {
                //binding.progressBar.visibility = View.VISIBLE
                when (it.status) {

                    Status.SUCCESS -> {
                        Log.v("status", "success")
                        // binding.progressBar.visibility = View.GONE
                        it.data?.let {

                            Log.v("dataHWNotification", it.toString())

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