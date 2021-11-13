package com.example.candles_guardian.representation.ui.notes

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.candles_guardian.representation.ui.notes.behaviour_notes.BehaviourNotesFragment
import com.example.candles_guardian.representation.ui.notes.eductional_notes.EductionalNotesFragment

class PageAdapterNotes(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return BehaviourNotesFragment()
            }
            1 -> {
                return EductionalNotesFragment()
            }
            else -> {
                return BehaviourNotesFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> {
                return "الملاحظات السلوكية"
            }
            1 -> {
                return "الملاحظات التعليمية"
            }
        }
        return super.getPageTitle(position)
    }

}