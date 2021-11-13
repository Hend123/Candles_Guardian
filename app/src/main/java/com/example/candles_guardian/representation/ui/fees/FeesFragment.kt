package com.example.candles_guardian.representation.ui.fees

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
import com.example.candles_guardian.databinding.FeesFragmentBinding
import com.example.candles_guardian.pojo.Fees
import com.example.candles_guardian.pojo.Stu
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.remote.ApiHelperImpl
import com.example.weatherforecast.utils.Status
import kotlinx.coroutines.flow.collect

class FeesFragment : Fragment() {
    private lateinit var childernItem: Stu
    private lateinit var viewModel: FeesViewModel
    private lateinit var feesAdapter: FeesAdapter
    private var _binding: FeesFragmentBinding? = null

    companion object {
        fun newInstance() = FeesFragment()
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
        _binding = FeesFragmentBinding.inflate(inflater, container, false)
        init()
        lifecycleScope.launchWhenStarted {
            getFees()
        }
        return binding.root
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(FeesViewModel::class.java)

        viewModel.setAtrribute(ApiHelperImpl(RetrofitClient.getApiService()))
        feesAdapter = FeesAdapter()
    }

    private fun initFeesRv(feesList: List<Fees>) {
        binding.feesRv.setHasFixedSize(true)
        binding.feesRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        feesAdapter.setDataAndContext(feesList, requireContext())
        binding.feesRv.adapter = feesAdapter
    }

    private suspend fun getFees() {
        viewModel.getFees("1132352046").collect {
            it.let {
                binding.progressBar2.visibility = View.VISIBLE
                when (it.status) {

                    Status.SUCCESS -> {
                        Log.v("status", "success")
                        binding.progressBar2.visibility = View.GONE
                        it.data?.let {
                            Log.v("dataFees", it.toString())
                            initFeesRv(it)
                        }
                    }
                    Status.LOADING -> {
                        binding.progressBar2.visibility = View.VISIBLE
                        Log.v("status", "loading")
                    }
                    Status.ERROR -> {
                       binding.progressBar2.visibility = View.GONE
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