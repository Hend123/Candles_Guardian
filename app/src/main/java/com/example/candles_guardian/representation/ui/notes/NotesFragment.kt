package com.example.candles_guardian.representation.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.candles_guardian.databinding.NotesFragmentBinding

class NotesFragment : Fragment() {
    private lateinit var viewModel: NotesViewModel
    private var _binding: NotesFragmentBinding? = null

    companion object {
        fun newInstance() = NotesFragment()
    }

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NotesFragmentBinding.inflate(inflater, container, false)
        init()
        binding.viewPager.adapter = PageAdapterNotes(childFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}