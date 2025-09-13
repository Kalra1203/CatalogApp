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
import com.example.catalogapp.data.CatalogRepository

class CatalogAdapter(
    private val onClick: (CatalogItem) -> Unit,
    private val onFavChange: () -> Unit // <-- callback to update fav count
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
            updateFavIcon(b.favButton, item.isFavourite)

            // Click to open detail
            b.root.setOnClickListener { onClick(item) }

            // Toggle favourite reactively
            val toggleFav = {
                if (item.isFavourite) CatalogRepository.removeFromFavourites(item)
                else CatalogRepository.addToFavourites(item)

                updateFavIcon(b.favButton, item.isFavourite)
                onFavChange() // <-- update fav count in fragment
            }

            b.favButton.setOnClickListener { toggleFav() }
            b.root.setOnLongClickListener {
                toggleFav()
                true
            }
        }

        private fun updateFavIcon(btn: ImageButton, fav: Boolean) {
            btn.setImageResource(
                if (fav) R.drawable.ic_heart_filled else R.drawable.ic_heart_outlined
            )
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<CatalogItem>() {
    override fun areItemsTheSame(oldItem: CatalogItem, newItem: CatalogItem) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: CatalogItem, newItem: CatalogItem) = oldItem.isFavourite == newItem.isFavourite &&
            oldItem.title == newItem.title &&
            oldItem.category == newItem.category &&
            oldItem.description == newItem.description &&
            oldItem.imageRes == newItem.imageRes
}
