package com.demo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {
    private val _isChecked = MutableLiveData<Pair<String, Boolean>>()
    val isChecked: LiveData<Pair<String, Boolean>> get() = _isChecked

    fun updateItemCheckState(
        id: String,
        isChecked: Boolean,
    ) {
        _isChecked.value = Pair(id, isChecked)
    }
}
