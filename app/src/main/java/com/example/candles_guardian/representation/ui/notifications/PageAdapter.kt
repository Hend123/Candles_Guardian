package com.example.candles_guardian.representation.ui.notifications

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.candles_guardian.representation.ui.notifications.quizes_notification.QuizesNotificationFragment
import com.example.candles_guardian.representation.ui.notifications.quizes_result_notification.QuizesResultNotificationFragment

class PageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
       return 2
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return QuizesNotificationFragment()
            }
            1 -> {
                return QuizesResultNotificationFragment()
            }
            else -> {
                return QuizesNotificationFragment()
            }
        }
    }
    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "الامتحانات"
            }
            1 -> {
                return "نتائج الامتحانات"
            }
        }
        return super.getPageTitle(position)
    }
}