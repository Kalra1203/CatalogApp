package com.example.catalogapp.data

import com.example.catalogapp.R
import com.example.catalogapp.model.CatalogItem

object CatalogRepository {
    private val items = mutableListOf<CatalogItem>()
    private val favourites = mutableSetOf<String>()

    init {
        // Load sample data directly here
        items.addAll(
            listOf(
                CatalogItem(
                    "1",
                    "Cottesloe Beach",
                    "Cottesloe Beach, located near Perth, Western Australia, is one of the region’s most iconic coastal destinations. Famous for its white sandy shores, turquoise waters, and stunning sunsets, it’s a popular spot for swimming, surfing, and snorkeling. The beach also offers lively cafes, grassy terraces, and scenic views year-round.",
                    "Beach",
                    R.drawable.item_1,
                    externalLink = "https://en.wikipedia.org/wiki/Cottesloe_Beach"
                ),
                CatalogItem(
                    "2",
                    "Kings Park",
                    "Kings Park in Perth, Western Australia, is one of the world’s largest inner-city parks, offering sweeping views of the Swan River and city skyline. It features the Botanic Garden with unique native flora, walking trails, and memorials. A blend of natural beauty and cultural heritage, it’s perfect for relaxation and exploration.",
                    "Park",
                    R.drawable.item_2,
                    externalLink = "https://en.wikipedia.org/wiki/Kings_Park,_Western_Australia"
                ),
                CatalogItem(
                    "3",
                    "Fremantle Market",
                    "Fremantle Markets, established in 1897, is a vibrant hub of culture and heritage in Fremantle, Western Australia. Known for its lively atmosphere, it hosts over 150 stalls offering fresh produce, local crafts, fashion, and street food. It’s a must-visit destination blending history, creativity, and community spirit in a bustling marketplace.",
                    "Historical",
                    R.drawable.item_3,
                    externalLink = "https://en.wikipedia.org/wiki/Fremantle_Markets"
                ),
                CatalogItem(
                    "4",
                    "Scarborough Beach",
                    "Scarborough Beach, located along Perth’s coastline, is a lively destination famous for its golden sands, clear waters, and surf-friendly waves. Popular for swimming, sunbathing, and beach sports, it also features cafes, restaurants, and a vibrant nightlife. With regular events and stunning sunsets, Scarborough offers the perfect mix of relaxation and fun.",
                    "Beach",
                    R.drawable.item_4,
                    externalLink = "https://en.wikipedia.org/wiki/Scarborough_Beach_Road"
                ),
                CatalogItem(
                    "5",
                    "Art Gallery WA",
                    "The Art Gallery of Western Australia (AGWA), located in Perth’s Cultural Centre, showcases an impressive collection of Australian, Indigenous, and international art. Featuring contemporary and historical works, it offers diverse exhibitions and programs. As a cultural landmark, AGWA inspires creativity, learning, and appreciation of the arts for visitors of all ages.",
                    "Museum",
                    R.drawable.item_5,
                    externalLink = "https://en.wikipedia.org/wiki/Art_Gallery_of_Western_Australia"
                ),
                CatalogItem(
                    "6",
                    "Elizabeth Quay",
                    "Elizabeth Quay, situated on the Swan River in Perth, is a vibrant waterfront precinct blending modern architecture with public art, dining, and entertainment. It features promenades, a striking suspension bridge, and lively event spaces. Popular with locals and tourists, Elizabeth Quay offers stunning river views and a dynamic city experience.",
                    "Landmark",
                    R.drawable.item_6,
                    externalLink = "https://en.wikipedia.org/wiki/Elizabeth_Quay"
                ),
                CatalogItem(
                    "7",
                    "Hyde Park",
                    "Hyde Park in Perth is a beautiful inner-city retreat known for its leafy avenues, shady plane trees, and peaceful lakes. Popular for picnics, walking, and family outings, it hosts community events and festivals. With playgrounds, open spaces, and abundant birdlife, Hyde Park offers a tranquil escape in the heart of the city",
                    "Park",
                    R.drawable.item_7,
                    externalLink = "https://en.wikipedia.org/wiki/Hyde_Park,_Sydney"
                ),
                CatalogItem(
                    "8",
                    "Perth Cultural Centre",
                    "The Perth Cultural Centre is a vibrant precinct bringing together Western Australia’s key arts and cultural institutions. Located in Northbridge, it includes the Art Gallery of WA, State Library, and WA Museum Boola Bardip. With public art, performances, and events, it serves as a lively hub for learning, creativity, and community..",
                    "Cultural",
                    R.drawable.item_8,
                    externalLink = "https://en.wikipedia.org/wiki/Perth_Cultural_Centre"
                ),
                CatalogItem(
                    "9",
                    "Swan Valley",
                    "Swan Valley, just outside Perth, is Western Australia’s oldest wine region, renowned for its vineyards, breweries, and gourmet food. Visitors enjoy wine tastings, fresh local produce, and artisan treats along the scenic Swan Valley Food and Wine Trail. With wildlife encounters, art galleries, and rich heritage, it offers a relaxing countryside escape.",
                    "Region",
                    R.drawable.item_9,
                    externalLink = "https://en.wikipedia.org/wiki/Swan_Valley_(Western_Australia)"
                ),
                CatalogItem(
                    "10",
                    "Heirisson Island",
                    "Heirisson Island, located on the Swan River in Perth, is a serene spot combining natural beauty with cultural significance. Known for its resident kangaroos and peaceful walking trails, it also features a striking statue of Noongar leader Yagan. The island offers relaxation, wildlife viewing, and insight into Indigenous heritage close to the city.",
                    "Nature",
                    R.drawable.item_10,
                    externalLink = "https://en.wikipedia.org/wiki/Heirisson_Island"
                )
            )
        )
    }

    fun getItems(): List<CatalogItem> = items

    fun getItemById(id: String): CatalogItem? =
        items.find { it.id == id }

    fun addToFavourites(item: CatalogItem) {
        favourites.add(item.id)
        item.isFavourite = true
    }

    fun removeFromFavourites(item: CatalogItem) {
        favourites.remove(item.id)
        item.isFavourite = false
    }

    fun getFavourites(): List<CatalogItem> =
        items.filter { favourites.contains(it.id) }
}
