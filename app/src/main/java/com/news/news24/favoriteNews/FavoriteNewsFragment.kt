package com.news.news24.favoriteNews

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.news.core.ui.FavoriteAdapter
import com.news.news24.databinding.FragmentFavoriteNewsBinding
import com.news.news24.detailsNews.DetailsNewsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteNewsFragment : Fragment() {

    private val favoriteNewsViewModel: FavoriteNewsViewModel by viewModels()

    private var _binding: FragmentFavoriteNewsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {


            val favoriteAdapter = FavoriteAdapter()
            favoriteAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailsNewsActivity::class.java)
                intent.putExtra(DetailsNewsActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            favoriteNewsViewModel.favoriteNews.observe(viewLifecycleOwner) { dataNews ->
                favoriteAdapter.setDataNews(dataNews)
                binding.viewError.root.visibility =
                    if (dataNews.isNotEmpty()) View.GONE else View.VISIBLE
            }

            with(binding.listNews) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = favoriteAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}