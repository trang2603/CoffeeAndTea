package com.demo.adapter

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.databinding.GridItemBinding
import com.demo.helper.FirestoreHelper
import com.demo.model.Item
import com.squareup.picasso.Picasso

class GridItemAdapter : ListAdapter<Item, GridItemAdapter.ViewHolder>(ItemCallback()) { /*(
    var listItem: List<Item>,
) : RecyclerView.Adapter<GridItemAdapter.ViewHolder>() {*/
    private val handler = Handler(Looper.getMainLooper())

    class ViewHolder(
        val binding: GridItemBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        with(holder.binding) {
            name.text = item.name
            Picasso.get().load(item.img).into(img)
            checkbox.setOnCheckedChangeListener(null)
            checkbox.isChecked = item.isChecked
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                Log.e("hello", "${item.id}-grid")
                item.isChecked = isChecked
                FirestoreHelper.updateItemCheckState(item.collectionPath, item.id, isChecked)
            }
        }
    }

//    override fun getItemCount(): Int = listItem.size
}
