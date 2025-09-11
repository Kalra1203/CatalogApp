package com.example.catalogapp.ui

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalogapp.R
import com.example.catalogapp.databinding.FragmentListBinding
import com.example.catalogapp.viewmodel.CatalogViewModel
import com.example.catalogapp.viewmodel.CatalogViewModelFactory

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CatalogViewModel by activityViewModels {
        CatalogViewModelFactory(requireActivity())
    }

    private lateinit var adapter: CatalogAdapter

    companion object { fun newInstance() = ListFragment() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Spinner setup
        val spinner: Spinner = binding.categorySpinner
        val categories = listOf("All") + (viewModel.allItems.value?.map { it.category }?.distinct() ?: emptyList())
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinner.setSelection(categories.indexOf(viewModel.category.value ?: "All"))
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.setCategory(categories[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // RecyclerView setup
        adapter = CatalogAdapter(
            onClick = { item ->
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_host, DetailFragment.newInstance(item))
                    .addToBackStack(null)
                    .commit()
            },
            onFavToggle = { itemId ->
                viewModel.toggleFavourite(itemId)
            }
        )

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        // Observe filtered items
        viewModel.filteredItems.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        // Observe favourites count
        viewModel.favourites.observe(viewLifecycleOwner) { favs ->
            binding.favCount.text = "Favorites: ${favs.size}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
