package com.example.candles_guardian.representation.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.candles_guardian.R
import com.example.candles_guardian.databinding.FragmentNotificationsBinding
import com.example.candles_guardian.pojo.Stu
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.remote.ApiHelperImpl
import com.example.weatherforecast.utils.Status
import kotlinx.coroutines.flow.collect

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
//    private lateinit var childernItem: Stu
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            childernItem = it.getParcelable<Stu>("childernItem")!!
//            Log.v("childern", childernItem.toString())
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        //init()
//        lifecycleScope.launchWhenStarted {
//            getHWNotification("1", "1", "1440")
//        }
        binding.viewPager.adapter = PageAdapter(childFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        return binding.root
    }

    private fun init() {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        notificationsViewModel.setAtrribute(ApiHelperImpl(RetrofitClient.getApiService()))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private suspend fun getHWNotification(
        classId: String,
        classRoomId: String,
        batchNumber: String
    ) {
        notificationsViewModel.getHWNotification(classId, classRoomId, batchNumber)
        notificationsViewModel.hwNotificationResponse.collect {
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