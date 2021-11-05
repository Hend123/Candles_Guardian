package com.example.candles_guardian.representation.ui.absence

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.candles_guardian.R

class AbsenceFragment : Fragment() {

    companion object {
        fun newInstance() = AbsenceFragment()
    }

    private lateinit var viewModel: AbsenceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.absence_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AbsenceViewModel::class.java)
        // TODO: Use the ViewModel
    }

}