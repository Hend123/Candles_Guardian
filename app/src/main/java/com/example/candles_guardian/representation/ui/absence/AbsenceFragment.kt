package com.example.candles_guardian.representation.ui.absence

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
import com.example.candles_guardian.R
import com.example.candles_guardian.databinding.AbsenceFragmentBinding
import com.example.candles_guardian.pojo.Absence
import com.example.candles_guardian.pojo.Stu
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.remote.ApiHelperImpl
import com.example.weatherforecast.utils.Status
import kotlinx.coroutines.flow.collect

class AbsenceFragment : Fragment() {
    private lateinit var childernItem: Stu
    private lateinit var viewModel: AbsenceViewModel

    private lateinit var absenceAdapter: AbsenceAdapter
    private var _binding: AbsenceFragmentBinding? = null

    companion object {
        fun newInstance() = AbsenceFragment()
    }

    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            childernItem = it.getParcelable<Stu>("childernItem")!!
            Log.v("childernItem", childernItem.toString())
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AbsenceFragmentBinding.inflate(inflater, container, false)
        init()
        lifecycleScope.launchWhenStarted {
            getAbsence()
        }
        return binding.root
    }


    private fun init() {
        viewModel = ViewModelProvider(this).get(AbsenceViewModel::class.java)

        viewModel.setAtrribute(ApiHelperImpl(RetrofitClient.getApiService()))
        absenceAdapter = AbsenceAdapter()
    }

    private fun initAbsenceRv(absenceList: List<Absence>) {
        binding.noAbsenceData.visibility = View.GONE
        binding.noAbsenceData.text = ""
        binding.absenceRv.setHasFixedSize(true)
        binding.absenceRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        absenceAdapter.setDataAndContext(absenceList, requireContext())
        binding.absenceRv.adapter = absenceAdapter
    }

    private suspend fun getAbsence() {
        viewModel.getAbsence("1132352046").collect {
            it.let {
                binding.progressBar3.visibility = View.VISIBLE
                when (it.status) {

                    Status.SUCCESS -> {
                        Log.v("status", "success")
                        binding.progressBar3.visibility = View.GONE
                        it.data?.let {
                            Log.v("dataAbsence", it.toString())
                            if (it.isNullOrEmpty()) {
                                binding.noAbsenceData.visibility = View.VISIBLE
                                binding.noAbsenceData.text = "لا يوجد ملاحظات!"
                            } else {
                                initAbsenceRv(it)
                            }
                        }
                    }
                    Status.LOADING -> {
                        binding.progressBar3.visibility = View.VISIBLE
                        Log.v("status", "loading")
                    }
                    Status.ERROR -> {
                        binding.progressBar3.visibility = View.GONE
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