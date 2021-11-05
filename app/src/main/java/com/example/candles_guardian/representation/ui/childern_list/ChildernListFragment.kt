package com.example.candles_guardian.representation.ui.childern_list

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.candles_guardian.R
import com.example.candles_guardian.databinding.FragmentChildernListBinding
import com.example.candles_guardian.pojo.Stu
import com.example.candles_guardian.pojo.User
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.remote.ApiHelperImpl
import com.example.weatherforecast.utils.Status
import com.google.gson.Gson
import kotlinx.coroutines.flow.collect

class ChildernListFragment : Fragment(), ChildernAdapter.OnItemClickListener {

    private lateinit var childernListViewModel: ChildernListViewModel
    private var _binding: FragmentChildernListBinding? = null
    private lateinit var childernAdapter: ChildernAdapter
    private lateinit var mPreferences: SharedPreferences
    private lateinit var mPrefsEditor: SharedPreferences.Editor
    private lateinit var gson: Gson
    private lateinit var stu: List<Stu>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChildernListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        init()
        setupUserNamAndType(getUser())
        lifecycleScope.launchWhenStarted {
            getChildern()
        }
        return binding.root
    }

    private fun init() {
        stu = ArrayList<Stu>()
        childernListViewModel =
            ViewModelProvider(this).get(ChildernListViewModel::class.java)
        childernListViewModel.setAtrribute(ApiHelperImpl(RetrofitClient.getApiService()))

        childernAdapter = ChildernAdapter()
        childernAdapter.setOnItemClickListener(this)
        mPreferences =
            requireActivity().getSharedPreferences("prefs", AppCompatActivity.MODE_PRIVATE)
        mPrefsEditor = mPreferences.edit()
        mPrefsEditor.putString("openChildernList", "open")
        mPrefsEditor.commit()
        gson = Gson()

    }

    private fun getUser(): User {
        val json = mPreferences.getString("user", "")
        val user = gson.fromJson(json, User::class.java)
        return user
    }

    private fun setupUserNamAndType(user_: User) {
        binding.user = user_
    }

    private suspend fun getChildern() {
        childernListViewModel.getChildern(getUser().password)
        childernListViewModel.childernsResponse.collect {
            it.let {
                binding.progressBar.visibility = View.VISIBLE
                when (it.status) {

                    Status.SUCCESS -> {
                        Log.v("status", "success")
                        binding.progressBar.visibility = View.GONE
                        it.data?.let {
                            Log.v("data", it.toString())
                            stu = it
                            initChildernRv(it)

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

    private fun initChildernRv(stuList: List<Stu>) {
        binding.childernRv.setHasFixedSize(true)
        binding.childernRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        childernAdapter.setDataAndContext(stuList, requireContext())
        binding.childernRv.adapter = childernAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onClick(position: Int) {
//        val childernItem = bundleOf("childernItem" to stu[position])
//        findNavController().navigate(
//            R.id.action_childernListFragment_to_detailsFragment2,
//            childernItem
//        )
//
//    }

    override fun onClickingMenu(position: Int, v: View) {
        val childernItem = bundleOf("childernItem" to stu[position])
        val popupMenu = PopupMenu(requireContext(), v)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.notesFragment2 ->
                    findNavController().navigate(
                        R.id.action_childernListFragment_to_notesFragment2,
                        childernItem
                    )
                R.id.absenceFragment2 ->
                    findNavController().navigate(
                        R.id.action_childernListFragment_to_absenceFragment2,
                        childernItem
                    )
                R.id.feesFragment2 ->
                    findNavController().navigate(
                        R.id.action_childernListFragment_to_feesFragment2,
                        childernItem
                    )
            }
            true
        })
        popupMenu.show()


    }
}