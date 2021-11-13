package com.example.candles_guardian.representation.ui.notifications.quizes_notification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.example.candles_guardian.R
import com.example.candles_guardian.databinding.QuizesNotificationFragmentBinding
import com.example.candles_guardian.pojo.HWNotification
import com.example.candles_guardian.pojo.Stu
import com.example.candles_guardian.representation.ui.notifications.work_manager.FetchDataAndSendNotificationQuizesWorker
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.remote.ApiHelperImpl
import com.example.weatherforecast.utils.Status
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.flow.collect
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit


class QuizesNotificationFragment : Fragment() {
    private var _binding: QuizesNotificationFragmentBinding? = null
    private lateinit var viewModel: QuizesNotificationViewModel
    private lateinit var workManager: WorkManager
    private lateinit var quizesAdapter: QuizesAdapter
    private lateinit var childernItem:Stu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            childernItem = it.getParcelable<Stu>("childernItem")!!
            Log.v("childernItemQ", childernItem.toString())
        }
    }


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
        if(!isWorkScheduled("workTagQuizes")) { // check if your work is not already scheduled
            setWorker("workTagQuizes") // schedule your work
        }


        return binding.root
    }


    private fun init() {
        viewModel = ViewModelProvider(this).get(QuizesNotificationViewModel::class.java)
        viewModel.setAtrribute(ApiHelperImpl(RetrofitClient.getApiService()))
        workManager = WorkManager.getInstance(requireContext())
        quizesAdapter = QuizesAdapter()
        binding.quizesRv.setHasFixedSize(true)
        binding.quizesRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    }

    private fun initQuizesRv(quizList: List<HWNotification>) {
        binding.noQuizes.visibility = View.GONE
        binding.noQuizes.text = ""
        quizesAdapter.setDataAndContext(quizList, requireContext())
        binding.quizesRv.adapter = quizesAdapter
    }

    private suspend fun getHWNotification(
        classId: String,
        classRoomId: String,
        batchNumber: String
    ) {
        viewModel.getHWNotification(classId, classRoomId, batchNumber)
        viewModel.hwNotificationResponse.collect {
            it.let {
                binding.progressBar.visibility = View.VISIBLE
                when (it.status) {

                    Status.SUCCESS -> {
                        Log.v("status", "success")
                        binding.progressBar.visibility = View.GONE
                        it.data?.let {

                            Log.v("dataHWNotification", it.toString())
                            if (it.isNullOrEmpty()) {
                                binding.noQuizes.visibility = View.VISIBLE
                                binding.noQuizes.text = " لايوجد اختبارات!"
                            } else {
                                initQuizesRv(it)
                            }


                        }
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        Log.v("status", "loading")
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
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

    private fun setWorker(workTag:String) {
        val data: Data = Data.Builder().putString("classId", "1").putString("classRoomId", "1")
            .putString("batchNo", "1440").build()
        val constrains = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val repeatingRequest = PeriodicWorkRequest.Builder(
            FetchDataAndSendNotificationQuizesWorker::class.java, 24,
            TimeUnit.HOURS
        )
            .addTag("PeriodicWork")
            .setConstraints(constrains)
            .setInputData(data)
            .build()
        workManager.enqueueUniquePeriodicWork(workTag,ExistingPeriodicWorkPolicy.KEEP,repeatingRequest)
        workManager.getWorkInfoByIdLiveData(repeatingRequest.id)
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                Log.v("state", it.state.name)

            })

    }
    private fun isWorkScheduled(tag: String): Boolean {
        val statuses: ListenableFuture<List<WorkInfo>> = workManager.getWorkInfosByTag(tag)
        return try {
            var running = false
            val workInfoList: List<WorkInfo> = statuses.get()
            for (workInfo in workInfoList) {
                val state = workInfo.state
                running = state == WorkInfo.State.RUNNING || state == WorkInfo.State.ENQUEUED
            }
            running
        } catch (e: ExecutionException) {
            e.printStackTrace()
            false
        } catch (e: InterruptedException) {
            e.printStackTrace()
            false
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}