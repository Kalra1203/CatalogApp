package com.example.catalogapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogapp.R
import com.example.catalogapp.databinding.ItemRowBinding
import com.example.catalogapp.model.CatalogItem

class CatalogAdapter(
    private val onClick: (CatalogItem) -> Unit,
    private val onFavToggle: (String) -> Unit
) : ListAdapter<CatalogItem, CatalogAdapter.VH>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    inner class VH(private val b: ItemRowBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: CatalogItem) {
            b.itemTitle.text = item.title
            b.itemCategory.text = item.category
            b.itemDesc.text = item.description
            b.itemImage.setImageResource(item.imageRes)
            updateFavIcon(b.favButton, item.isFavourite) // Boolean
            b.root.setOnClickListener { onClick(item) }
            b.root.setOnLongClickListener {
                onFavToggle(item.id)
                true
            }
            b.favButton.setOnClickListener {
                onFavToggle(item.id)
            }
        }

        private fun updateFavIcon(btn: ImageButton, fav: Boolean) {
            btn.setImageResource(if (fav) R.drawable.ic_heart_filled else R.drawable.ic_heart_outlined)
        }
    }

}

class DiffCallback : DiffUtil.ItemCallback<CatalogItem>() {
    override fun areItemsTheSame(oldItem: CatalogItem, newItem: CatalogItem) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: CatalogItem, newItem: CatalogItem) = oldItem == newItem
}
