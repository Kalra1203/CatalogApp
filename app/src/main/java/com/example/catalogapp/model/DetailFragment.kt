package com.example.catalogapp.model

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.catalogapp.R
import com.example.catalogapp.data.CatalogRepository
import com.example.catalogapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var item: CatalogItem

    companion object {
        private const val ARG_ITEM = "arg_item"
        fun newInstance(item: CatalogItem) = DetailFragment().apply {
            arguments = Bundle().apply { putParcelable(ARG_ITEM, item) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item = requireNotNull(arguments?.getParcelable(ARG_ITEM)) { "CatalogItem missing" }

        binding.detailTitle.text = item.title
        binding.detailDesc.text = item.description
        binding.detailImage.setImageResource(item.imageRes)
        updateFavUi(item.isFavourite)

        binding.detailFav.setOnClickListener {
            if (item.isFavourite) CatalogRepository.removeFromFavourites(item)
            else CatalogRepository.addToFavourites(item)
            updateFavUi(item.isFavourite)
        }

        binding.openLink.setOnClickListener {
            item.externalLink?.let { url -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
        }

        binding.closeButton.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun updateFavUi(isFav: Boolean) {
        binding.detailFav.setImageResource(if (isFav) R.drawable.ic_heart_filled else R.drawable.ic_heart_outlined)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
