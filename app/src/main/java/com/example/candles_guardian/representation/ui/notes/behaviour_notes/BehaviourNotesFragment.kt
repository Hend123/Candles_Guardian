package com.example.candles_guardian.representation.ui.notes.behaviour_notes

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
import com.example.candles_guardian.databinding.BehaviourNotesFragmentBinding
import com.example.candles_guardian.pojo.Stu
import com.example.candles_guardian.pojo.StuBehaviourNote
import com.example.candles_guardian.representation.ui.notes.NotesAdapter
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.remote.ApiHelperImpl
import com.example.weatherforecast.utils.Status
import kotlinx.coroutines.flow.collect

class BehaviourNotesFragment : Fragment() {
    private lateinit var viewModel: BehaviourNotesViewModel
    private lateinit var childernItem: Stu
    private lateinit var notesAdapter: NotesAdapter
    private var _binding: BehaviourNotesFragmentBinding? = null

    companion object {
        fun newInstance() = BehaviourNotesFragment()
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
    ): View {
        _binding = BehaviourNotesFragmentBinding.inflate(inflater, container, false)
//
//        init()
//        lifecycleScope.launchWhenStarted {
//            getStuBehaviourNotes()
//        }
        return binding.root
    }


    private fun init() {
        Log.v("start", "behaviour")
        viewModel = ViewModelProvider(this).get(BehaviourNotesViewModel::class.java)

        viewModel.setAtrribute(ApiHelperImpl(RetrofitClient.getApiService()))
        notesAdapter = NotesAdapter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        lifecycleScope.launchWhenStarted {
            getStuBehaviourNotes()
        }
    }

    private suspend fun getStuBehaviourNotes() {
        viewModel.getStuBehaviourNotes("1132352046")
        viewModel.stuBehaviourNotesResponse.collect {
            it.let {
                binding.progressBar.visibility = View.VISIBLE
                when (it.status) {

                    Status.SUCCESS -> {
                        Log.v("status", "success")
                        binding.progressBar.visibility = View.GONE
                        it.data?.let {
                            Log.v("dataBehaviourNotes", it.toString())
                            if (it.isNullOrEmpty()) {
                                binding.nostuBehaviourNotes.visibility = View.VISIBLE
                                binding.nostuBehaviourNotes.text = "لا يوجد ملاحظات!"
                            } else {
                                initstuBehaviourNotesRv(it)

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

    private fun initstuBehaviourNotesRv(noteList: List<StuBehaviourNote>) {
        binding.nostuBehaviourNotes.visibility = View.GONE
        binding.nostuBehaviourNotes.text = ""
        binding.stuBehaviourNotesRv.setHasFixedSize(true)
        binding.stuBehaviourNotesRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        notesAdapter.setDataAndContext(noteList, requireContext())
//        notesAdapter.notifyDataSetChanged()
        binding.stuBehaviourNotesRv.adapter = notesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}