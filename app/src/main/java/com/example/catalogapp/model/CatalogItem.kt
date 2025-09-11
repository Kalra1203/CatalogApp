package com.example.catalogapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatalogItem(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val imageRes: Int,
    var isFavourite: Boolean = false,
    val externalLink: String? = null
) : Parcelable
