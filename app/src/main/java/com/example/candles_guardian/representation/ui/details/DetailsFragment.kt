package com.example.candles_guardian.representation.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.candles_guardian.R
import com.example.candles_guardian.databinding.DetailsFragmentBinding
import com.example.candles_guardian.databinding.FragmentChildernListBinding
import com.example.candles_guardian.pojo.Fees
import com.example.candles_guardian.pojo.Stu
import com.example.candles_guardian.pojo.StuBehaviourNote
import com.example.candles_guardian.representation.ui.childern_list.ChildernListViewModel
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.remote.ApiHelperImpl
import com.example.weatherforecast.utils.Resource
import com.example.weatherforecast.utils.Status
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {
    private lateinit var childernItem: Stu
    private lateinit var viewModel: DetailsViewModel
    private lateinit var notesAdapter: NotesAdapter
    private var _binding: DetailsFragmentBinding? = null

    companion object {
        fun newInstance() = DetailsFragment()
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
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.stuDetails = childernItem
        init()
        lifecycleScope.launchWhenStarted {
            val stuBehaviourNotes = async { getStuBehaviourNotes() }
            val stuEductionalNotes = async { getStuEductionalNotes() }
            val fees = async { getFees() }
            status(stuBehaviourNotes.await()!!, 1)
            status(stuEductionalNotes.await()!!, 2)
            Log.v("fees",fees.await().toString())

        }
        return binding.root
    }

    private fun init() {
        viewModel =
            ViewModelProvider(this).get(DetailsViewModel::class.java)
        viewModel.setAtrribute(ApiHelperImpl(RetrofitClient.getApiService()))
        notesAdapter = NotesAdapter()
    }

    private fun initstuBehaviourNotesRv(noteList: List<StuBehaviourNote>) {
        binding.nostuBehaviourNotes.visibility = View.GONE
        binding.nostuBehaviourNotes.text = ""
        binding.stuBehaviourNotesRv.setHasFixedSize(true)
        binding.stuBehaviourNotesRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        notesAdapter.setDataAndContext(noteList, requireContext())
        notesAdapter.notifyDataSetChanged()
        binding.stuBehaviourNotesRv.adapter = notesAdapter
    }

    private fun initstuEductionalNotesRv(noteList: List<StuBehaviourNote>) {
        binding.nostuEductionalNotes.visibility = View.GONE
        binding.nostuEductionalNotes.text = ""
        binding.stuEductionalNotesRv.setHasFixedSize(true)
        binding.stuEductionalNotesRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        notesAdapter.setDataAndContext(noteList, requireContext())
        notesAdapter.notifyDataSetChanged()

        binding.stuEductionalNotesRv.adapter = notesAdapter
    }

    private suspend fun getStuBehaviourNotes(): Resource<List<StuBehaviourNote>>? {
        var data: Resource<List<StuBehaviourNote>>? = null
        viewModel.getStuBehaviourNotes("1132352046")
        viewModel.stuBehaviourNotesResponse.collect {
            it.let {
                binding.progressBar.visibility = View.VISIBLE
                data = it
                Log.v("dataBehaviourNotes", it.toString())
                // status(it, 1)
            }
        }
        return data
    }

    private suspend fun getFees():List<Fees>? {
        var data: List<Fees>? = null
        viewModel.getFees("1132352046")
        viewModel.feesResponse.collect {
            it.let {
                binding.progressBar.visibility = View.VISIBLE
                when (it.status) {

                    Status.SUCCESS -> {
                        Log.v("status", "success")
                        binding.progressBar.visibility = View.GONE
                        it.data?.let {
                           data = it
                            Log.v("dataFees", it.toString())
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
        return data
    }

    private suspend fun getStuEductionalNotes():Resource<List<StuBehaviourNote>>? {
        var data: Resource<List<StuBehaviourNote>>? = null
        viewModel.getStuEductionalNotes("1132352046")
        viewModel.stuEductionalNotesResponse.collect {
            it.let {
                binding.progressBar.visibility = View.VISIBLE
                data = it
                Log.v("dataEductionalNotes", it.toString())
                //status(it, 2)
            }
        }
        return data
    }

    private fun status(it: Resource<List<StuBehaviourNote>>, flag: Int) {
        when (it.status) {
            Status.SUCCESS -> {
                Log.v("status", "success")
                binding.progressBar.visibility = View.GONE
                it.data?.let {

                    Log.v("data", it.toString())
                    if (flag == 1) {
                        if (it.isNullOrEmpty()) {
                            binding.nostuBehaviourNotes.visibility = View.VISIBLE
                            binding.nostuBehaviourNotes.text = "لا يوجد ملاحظات!"
                        } else {
                            initstuBehaviourNotesRv(it)

                        }
                    } else {
                        if (it.isNullOrEmpty()) {
                            Log.v("empty", it.toString() + "kk")
                            binding.nostuEductionalNotes.visibility = View.VISIBLE
                            binding.nostuEductionalNotes.text = "لا يوجد ملاحظات!"
                        } else {
                            initstuEductionalNotesRv(it)
                        }

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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}