package com.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.databinding.RecycleItemBinding
import com.demo.model.Item
import com.squareup.picasso.Picasso

class RecycleViewAdapter : ListAdapter<Item, RecycleViewAdapter.ViewHolder>(ItemCallback()) {
    //
    class ViewHolder(
        val binding: RecycleItemBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = RecycleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        with(holder.binding) {
            name.text = item.name.capitalizeFirstLetterOnly()
            Picasso.get().load(item.img).into(img)
            mount.text = item.mount.toString()
            btnMinus.setOnClickListener {
                if (item.mount > 1) {
                    item.mount--
                    mount.text = item.mount.toString()
                }
                if (item.mount == 1) {
                    AlertDialog
                        .Builder(holder.itemView.context)
                        .setTitle("Thông báo")
                        .setMessage("Bạn có muốn xóa sản phẩm này không?")
                        .setPositiveButton("Có") { _, _ ->
                            item.isChecked = false
                        }.setNegativeButton("Không") { dialog, _ ->
                            dialog.dismiss()
                        }.show()
                }
            }
            btnPlus.setOnClickListener {
                item.mount++
                mount.text = item.mount.toString()
            }
            // price.text = item.price
        }
    }

    private fun displayDialog() {
    }

//    override fun getItemCount(): Int = list.size

    fun String.capitalizeFirstLetterOnly(): String =
        this.lowercase().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
}
