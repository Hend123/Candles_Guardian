package com.example.candles_guardian.representation.ui.notifications.quizes_result_notification

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
import com.example.candles_guardian.databinding.QuizesResultNotificationFragmentBinding
import com.example.candles_guardian.pojo.QuizResult
import com.example.candles_guardian.pojo.Stu
import com.example.candles_guardian.representation.ui.notifications.work_manager.FetchDataAndSendNotificationQuizesResultWorker
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.remote.ApiHelperImpl
import com.example.weatherforecast.utils.Status
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.flow.collect
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

class QuizesResultNotificationFragment : Fragment() {
    private var _binding: QuizesResultNotificationFragmentBinding? = null
    private lateinit var viewModel: QuizesResultNotificationViewModel
    private lateinit var quizesResultAdapter: QuizesResultAdapter
    private lateinit var workManager: WorkManager
    private lateinit var childernItem: Stu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            childernItem = it.getParcelable<Stu>("childernItem")!!
            Log.v("childernItemQR", childernItem.toString())
        }
    }

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
            getQuizResult("1128971916", "1440")
        }
        if(!isWorkScheduled("workTagQuizesResult")) { // check if your work is not already scheduled
            setWorker("workTagQuizesResult") // schedule your work
        }
        return binding.root
    }


    private fun init() {
        viewModel = ViewModelProvider(this).get(QuizesResultNotificationViewModel::class.java)
        viewModel.setAtrribute(ApiHelperImpl(RetrofitClient.getApiService()))
        workManager = WorkManager.getInstance(requireContext())
        quizesResultAdapter = QuizesResultAdapter()
        binding.quizesResultRv.setHasFixedSize(true)
        binding.quizesResultRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    }

    private fun initQuizesResultRv(quizResultList: List<QuizResult>) {
        binding.noQuizesResult.visibility = View.GONE
        binding.noQuizesResult.text = ""
        quizesResultAdapter.setDataAndContext(quizResultList, requireContext())
        binding.quizesResultRv.adapter = quizesResultAdapter
    }

    private suspend fun getQuizResult(
        studentId: String,
        batchNumber: String
    ) {
        viewModel.getQuizResult(studentId, batchNumber)
        viewModel.quizResultNotificationResponse.collect {
            it.let {
                binding.progressBar.visibility = View.VISIBLE
                when (it.status) {

                    Status.SUCCESS -> {
                        Log.v("status", "success")
                        binding.progressBar.visibility = View.GONE
                        it.data?.let {

                            Log.v("dataQuizResultNot", it.toString())
                            if (it.isNullOrEmpty()) {
                                binding.noQuizesResult.visibility = View.VISIBLE
                                binding.noQuizesResult.text = " لايوجد اختبارات!"
                            } else {
                                initQuizesResultRv(it)
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
        val data: Data = Data.Builder().putString("studentId", "1128971916")
            .putString("batchNo", "1440").build()
        val constrains = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val repeatingRequest = PeriodicWorkRequest.Builder(
            FetchDataAndSendNotificationQuizesResultWorker::class.java, 24,
            TimeUnit.HOURS
        )
            .addTag("PeriodicWork")
            .setConstraints(constrains)
            .setInputData(data)
            .build()
        workManager.enqueueUniquePeriodicWork(workTag,ExistingPeriodicWorkPolicy.KEEP,repeatingRequest)
        workManager.getWorkInfoByIdLiveData(repeatingRequest.id)
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                Log.v("stateQuizResult", it.state.name)

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