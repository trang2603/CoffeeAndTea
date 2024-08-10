package com.demo.fragment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.adapter.RecycleViewAdapter
import com.demo.databinding.FragmentRecycleviewBinding
import com.demo.helper.FirestoreHelper
import com.demo.viewModel.ItemViewModel
import com.google.firebase.firestore.FirebaseFirestore

class SelectedItemsFragment : Fragment() {
    private lateinit var binding: FragmentRecycleviewBinding
    private lateinit var recycleViewAdapter: RecycleViewAdapter
    private lateinit var viewModel: ItemViewModel
    private val firestore = FirebaseFirestore.getInstance()
    val collections =
        listOf(
            firestore.collection("coffee"),
            firestore.collection("tea"),
            firestore.collection("freeze"),
            firestore.collection("food"),
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRecycleviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)
        binding.recycleViewSelectedItem.layoutManager = LinearLayoutManager(context)
        recycleViewAdapter = RecycleViewAdapter()
        binding.recycleViewSelectedItem.adapter = recycleViewAdapter
        FirestoreHelper.fetchData(collections, recycleViewAdapter)
    }
}
