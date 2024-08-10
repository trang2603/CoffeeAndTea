package com.demo.fragment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.demo.adapter.FragmentAdapter2
import com.demo.databinding.FragmentTablayoutBinding
import com.demo.helper.FirestoreHelper
import com.demo.viewModel.ItemViewModel
import com.google.android.material.tabs.TabLayoutMediator

class TabFragment : Fragment() {
    private lateinit var adapter: FragmentAdapter2
    private lateinit var binding: FragmentTablayoutBinding
    private lateinit var viewModel: ItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTablayoutBinding.inflate(inflater, container, false)
        setupViewPagerAndTabs()

        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        FirestoreHelper.setViewModel(viewModel)
        return binding.root
    }

    private fun setupViewPagerAndTabs() {
        adapter = FragmentAdapter2(this)
        binding.viewPager2.adapter = adapter

        binding.viewPager2.offscreenPageLimit = 1

        TabLayoutMediator(binding.tablayout, binding.viewPager2) { tab, pos ->
            tab.text =
                when (pos) {
                    0 -> "Tất cả"
                    1 -> "Cà phê"
                    2 -> "Trà"
                    3 -> "Freeze"
                    else -> "Bánh ngọt"
                }
        }.attach()
    }
}
