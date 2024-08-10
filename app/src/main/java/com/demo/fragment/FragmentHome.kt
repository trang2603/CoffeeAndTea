package com.demo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.demo.databinding.FragmentHomeBinding
import com.demo.fragment2.SelectedItemsFragment
import com.demo.fragment2.TabFragment
import com.demo.helper.FirestoreHelper

class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        FirestoreHelper.setAllFieldToFalse("coffee")
        FirestoreHelper.setAllFieldToFalse("tea")
        FirestoreHelper.setAllFieldToFalse("freeze")
        FirestoreHelper.setAllFieldToFalse("food")

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        if (savedInstanceState == null) {
            childFragmentManager.commit {
                replace(binding.tabFragmentContainer.id, TabFragment())
                    .replace(binding.selectedItemsFragmentContainer.id, SelectedItemsFragment())
            }
        }

        return binding.root
    }
}
