package com.example.candles_guardian.representation.ui.notes.eductional_notes

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
import com.example.candles_guardian.databinding.EductionalNotesFragmentBinding
import com.example.candles_guardian.pojo.Stu
import com.example.candles_guardian.pojo.StuBehaviourNote
import com.example.candles_guardian.representation.ui.notes.NotesAdapter
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.remote.ApiHelperImpl
import com.example.weatherforecast.utils.Status
import kotlinx.coroutines.flow.collect

class EductionalNotesFragment : Fragment() {
    private lateinit var childernItem: Stu
    private var _binding: EductionalNotesFragmentBinding? = null
    private lateinit var viewModel: EductionalNotesViewModel
    private lateinit var notesAdapter: NotesAdapter


    companion object {
        fun newInstance() = EductionalNotesFragment()
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
        _binding = EductionalNotesFragmentBinding.inflate(inflater, container, false)
//        init()
//        lifecycleScope.launchWhenStarted {
//            getStuEductionalNotes()
//        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        lifecycleScope.launchWhenStarted {
            getStuEductionalNotes()
        }

    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(EductionalNotesViewModel::class.java)

        viewModel.setAtrribute(ApiHelperImpl(RetrofitClient.getApiService()))
        notesAdapter = NotesAdapter()
    }

    private fun initstuEductionalNotesRv(noteList: List<StuBehaviourNote>) {
        binding.nostuEductionalNotes.visibility = View.GONE
        binding.nostuEductionalNotes.text = ""
        binding.stuEductionalNotesRv.setHasFixedSize(true)
        binding.stuEductionalNotesRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        notesAdapter.setDataAndContext(noteList, requireContext())
       // notesAdapter.notifyDataSetChanged()

        binding.stuEductionalNotesRv.adapter = notesAdapter
    }

    private suspend fun getStuEductionalNotes() {
        viewModel.getStuEductionalNotes("1132352046")
        viewModel.stuEductionalNotesResponse.collect {
            it.let {
              binding.progressBar.visibility = View.VISIBLE
                when (it.status) {

                    Status.SUCCESS -> {
                        Log.v("status", "success")
                        binding.progressBar.visibility = View.GONE
                        it.data?.let {
                            Log.v("dataEductionalNotes", it.toString())
                            if (it.isNullOrEmpty()) {
                                Log.v("empty", it.toString() + "kk")
                                binding.nostuEductionalNotes.visibility = View.VISIBLE
                                binding.nostuEductionalNotes.text = "لا يوجد ملاحظات!"
                            } else {
                                initstuEductionalNotesRv(it)
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


}