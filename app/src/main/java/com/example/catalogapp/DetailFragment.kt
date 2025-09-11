package com.example.catalogapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.catalogapp.R
import com.example.catalogapp.databinding.FragmentDetailBinding
import com.example.catalogapp.model.CatalogItem
import com.example.catalogapp.viewmodel.CatalogViewModel
import com.example.catalogapp.viewmodel.CatalogViewModelFactory

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val vm: CatalogViewModel by activityViewModels {
        CatalogViewModelFactory(requireActivity())
    }

    private lateinit var item: CatalogItem

    companion object {
        private const val ARG_ITEM = "arg_item"

        fun newInstance(item: CatalogItem): DetailFragment {
            val fragment = DetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_ITEM, item)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the passed CatalogItem
        item = requireArguments().getParcelable(ARG_ITEM)!!

        // Set UI
        binding.detailTitle.text = item.title
        binding.detailDesc.text = item.description
        binding.detailImage.setImageResource(item.imageRes)
        updateFavUi(item.isFavourite)

        // Observe favourites to update UI dynamically
        vm.favourites.observe(viewLifecycleOwner) { favs ->
            updateFavUi(favs.contains(item.id))
        }

        // Toggle favourite
        binding.detailFav.setOnClickListener {
            vm.toggleFavourite(item.id)
        }

        // Open external link
        binding.openLink.setOnClickListener {
            item.externalLink?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }

        // Close fragment
        binding.closeButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun updateFavUi(isFav: Boolean) {
        binding.detailFav.setImageResource(
            if (isFav) R.drawable.ic_heart_filled else R.drawable.ic_heart_outlined
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
