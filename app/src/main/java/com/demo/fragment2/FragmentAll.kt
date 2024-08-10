package com.demo.fragment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.adapter.GridItemAdapter
import com.demo.adapter.ItemCallback
import com.demo.databinding.FragmentAllBinding
import com.demo.helper.FirestoreHelper
import com.demo.model.Item
import com.demo.viewModel.ItemViewModel
import com.google.firebase.firestore.FirebaseFirestore

class FragmentAll : Fragment() {
    private lateinit var binding: FragmentAllBinding
    private lateinit var gridItemAdapter: GridItemAdapter
    private val firestore = FirebaseFirestore.getInstance()
    private val collections =
        listOf(
            firestore.collection("coffee"),
            firestore.collection("tea"),
            firestore.collection("freeze"),
            firestore.collection("food"),
        )
    private lateinit var viewModel: ItemViewModel
    private val items = mutableListOf<Item>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)
        FirestoreHelper.setViewModel(viewModel)
        binding.recycleViewAll.layoutManager = GridLayoutManager(requireContext(), 4)
        gridItemAdapter = GridItemAdapter()
        binding.recycleViewAll.adapter = gridItemAdapter
        FirestoreHelper.fetchAllDataFromFirestore(collections, gridItemAdapter)

        /*viewModel.isChecked.observe(
            viewLifecycleOwner,
            { (id, isChecked) ->
                val itemIndex = items.indexOfFirst { it.id == id }
                if (itemIndex != -1 && items[itemIndex].isChecked != isChecked) {
                    items[itemIndex].isChecked = isChecked
                    gridItemAdapter.notifyItemChanged(itemIndex)
                }
            },
        )*/
        val differ = AsyncListDiffer(gridItemAdapter, ItemCallback())
        viewModel.isChecked.observe(viewLifecycleOwner) { (id, isChecked) ->
            val itemList = gridItemAdapter.currentList.toMutableList()
            val itemIndex = itemList.indexOfFirst { it.id == id }

            if (itemIndex != -1) {
                val updatedItem = itemList[itemIndex].copy(isChecked = isChecked)
                itemList[itemIndex] = updatedItem
                gridItemAdapter.submitList(itemList.toList()) // Convert to a new list to trigger diffing
            }
            /*val updatedItems =
                items.map {
                    if (it.id == id) {
                        it.copy(isChecked = isChecked)
                    } else {
                        it
                    }
                }
            differ.submitList(updatedItems)*/
        }
    }
}
