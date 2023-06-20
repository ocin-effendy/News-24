package com.news.news24.detailsNews

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.news.core.domain.model.News
import com.news.news24.R
import com.news.news24.databinding.ActivityDetailsNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsNewsBinding
    private lateinit var detailsNewsViewModel: DetailsNewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailsNewsViewModel = ViewModelProvider(this).get(DetailsNewsViewModel::class.java)


        val detailNews = intent.getParcelableExtra<News>(EXTRA_DATA)
        if (detailNews != null) {
            showDetailNews(detailNews)
        }
    }


    private fun showDetailNews(detailNews: News) {

        Glide.with(this)
            .load(detailNews.urlToImage)
            .into(binding.heroImages)

        binding.newsTitle.text = detailNews.title
        binding.newsAuthor.text = detailNews.author
        binding.newsContent.text = detailNews.content



        val isFavoriteLiveData = detailsNewsViewModel.searchFavoriteNews(detailNews.title)

        isFavoriteLiveData.observe(this) { result ->
            if (result != null) {
                Log.e("search", result.title)
                binding.iconSaved.setImageResource(R.drawable.baseline_bookmark_24)
            }
        }

        binding.iconSaved.setOnClickListener {
            val isFavorite = isFavoriteLiveData.value != null
            if (isFavorite) {
                detailsNewsViewModel.deleteFavoriteNews(detailNews.title)
                binding.iconSaved.setImageResource(R.drawable.outline_bookmark_border_24)
                Toast.makeText(this, MESSAGE_DELETED, Toast.LENGTH_SHORT).show()
            } else {
                detailsNewsViewModel.setFavoriteNews(detailNews)
                Toast.makeText(this, MESSAGE_SAVED, Toast.LENGTH_SHORT).show()
                binding.iconSaved.setImageResource(R.drawable.baseline_bookmark_24)
            }
        }

        binding.iconBack.setOnClickListener {
            onBackPressed()
        }
    }


    companion object {
        const val EXTRA_DATA = "extra_data"
        const val MESSAGE_SAVED = "News Saved"
        const val MESSAGE_DELETED = "News DELETED"

    }
}