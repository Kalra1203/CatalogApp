package com.example.catalogapp.viewmodel

import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.catalogapp.data.SampleRepository
import com.example.catalogapp.model.CatalogItem

class CatalogViewModel(private val savedState: SavedStateHandle) : ViewModel() {

    // All catalog items
    private val _allItems = MutableLiveData<List<CatalogItem>>(SampleRepository.getItems())
    val allItems: LiveData<List<CatalogItem>> = _allItems

    // Search query
    private val _search = MutableLiveData(savedState.get<String>("search") ?: "")
    val search: LiveData<String> = _search

    // Selected category
    private val _category = MutableLiveData(savedState.get<String>("category") ?: "All")
    val category: LiveData<String> = _category

    // Favourites stored as MutableSet internally
    private val _favourites = MutableLiveData(
        savedState.get<HashSet<String>>("favs")?.toMutableSet() ?: mutableSetOf()
    )

    // Expose as immutable Set to UI
    val favourites: LiveData<Set<String>> = _favourites.map { it.toSet() }

    // Filtered items based on search, category, and favourites
    val filteredItems: LiveData<List<CatalogItem>> = MediatorLiveData<List<CatalogItem>>().apply {
        fun update() {
            val items = _allItems.value ?: emptyList()
            val query = _search.value.orEmpty().trim().lowercase()
            val cat = _category.value ?: "All"
            val favs = _favourites.value ?: emptySet()

            val filtered = items.filter { item ->
                val matchesText = item.title.lowercase().contains(query) ||
                        item.description.lowercase().contains(query)
                val matchesCategory = (cat == "All") || (item.category == cat)
                matchesText && matchesCategory
            }.map { item ->
                item.copy(isFavourite = favs.contains(item.id))
            }

            this.value = filtered
        }

        addSource(_allItems) { update() }
        addSource(_search) { savedState.set("search", it); update() }
        addSource(_category) { savedState.set("category", it); update() }
        addSource(_favourites) { savedState.set("favs", HashSet(it)); update() }
    }

    // Update search query
    fun setSearch(q: String) {
        _search.value = q
    }


    // Update category
    fun setCategory(c: String) {
        _category.value = c
    }

    // Toggle favourite status
    fun toggleFavourite(itemId: String) {
        val set = _favourites.value?.toMutableSet() ?: mutableSetOf()
        if (set.contains(itemId)) set.remove(itemId) else set.add(itemId)
        _favourites.value = set
    }
}

// ViewModel Factory for SavedStateHandle
class CatalogViewModelFactory(
    owner: SavedStateRegistryOwner,
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T {
        return CatalogViewModel(handle) as T
    }
}
