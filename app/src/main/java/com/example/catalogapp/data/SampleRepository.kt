package com.example.catalogapp.data

import com.example.catalogapp.R
import com.example.catalogapp.model.CatalogItem

object SampleRepository {
    fun getItems(): List<CatalogItem> = listOf(
        CatalogItem("1", "Cottesloe Beach", "Popular beach", "Beach", R.drawable.item1, externalLink = "https://en.wikipedia.org/wiki/Cottesloe_Beach"),
        CatalogItem("2","Kings Park","Large park with city views and memorial.","Park", R.drawable.item2),
        CatalogItem("3","Fremantle Market","Historic market with crafts & food.","Historical", R.drawable.item3),
        CatalogItem("4","Scarborough Beach","Surf-friendly beach with promenade.","Beach", R.drawable.item4),
        CatalogItem("5","Art Gallery WA","State art gallery with rotating exhibits.","Museum", R.drawable.item5),
        CatalogItem("6","Elizabeth Quay","Waterfront precinct with dining & events.","Landmark", R.drawable.item6),
        CatalogItem("7","Hyde Park","Green space with birdlife and lakes.","Park", R.drawable.item7),
        CatalogItem("8","Perth Cultural Centre","Hub for arts and museums.","Cultural", R.drawable.item8),
        CatalogItem("9","Swan Valley","Winery region offering tours & tastings.","Region", R.drawable.item9),
        CatalogItem("10","Heirisson Island","Island in Swan River with kangaroos.","Nature", R.drawable.item10)
    )
}
