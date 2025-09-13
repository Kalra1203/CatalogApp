package com.example.catalogapp.model

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalogapp.data.CatalogRepository
import com.example.catalogapp.databinding.FragmentListBinding
import com.example.catalogapp.ui.CatalogAdapter

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CatalogAdapter
    private var currentSearchQuery: String = ""

    companion object {
        fun newInstance() = ListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView setup
        adapter = CatalogAdapter(
            onClick = { item ->
                parentFragmentManager.beginTransaction()
                    .replace(com.example.catalogapp.R.id.fragment_host, DetailFragment.newInstance(item))
                    .addToBackStack(null)
                    .commit()
            },
            onFavChange = { updateFavCount() } // <-- real-time fav count update
        )
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        // Spinner setup
        val categories = listOf("All") + CatalogRepository.getItems().map { it.category }.distinct()
        binding.categorySpinner.adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, categories).apply {
            setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        }

        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filterList(currentSearchQuery)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Initial list
        filterList("")
    }

    // Call this from MainActivity SearchView or anywhere
    fun filterList(query: String) {
        currentSearchQuery = query
        val selectedCategory = binding.categorySpinner.selectedItem as String

        val filtered = CatalogRepository.getItems().filter { item ->
            val matchesText = item.title.contains(query, ignoreCase = true) ||
                    item.description.contains(query, ignoreCase = true)
            val matchesCategory = (selectedCategory == "All") || (item.category == selectedCategory)
            matchesText && matchesCategory
        }

        adapter.submitList(filtered)
        updateFavCount()
    }

    private fun updateFavCount() {
        binding.favCount.text = "Favorites: ${CatalogRepository.getFavourites().size}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
