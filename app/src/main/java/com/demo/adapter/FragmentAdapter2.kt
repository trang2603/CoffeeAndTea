package com.demo.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.demo.fragment2.FragmentAll
import com.demo.fragment2.FragmentCoffee
import com.demo.fragment2.FragmentFood
import com.demo.fragment2.FragmentFreeze
import com.demo.fragment2.FragmentTea

class FragmentAdapter2(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FragmentAll()
            1 -> FragmentCoffee()
            2 -> FragmentTea()
            3 -> FragmentFreeze()
            else -> FragmentFood()
        }
}
