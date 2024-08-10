package com.demo.adapter

import androidx.recyclerview.widget.DiffUtil
import com.demo.model.Item

class ItemCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(
        oldItem: Item,
        newItem: Item,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Item,
        newItem: Item,
    ): Boolean = oldItem == newItem
}
