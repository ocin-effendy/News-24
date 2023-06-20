package com.news.news24

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.news.news24.databinding.ActivityMainBinding
import com.news.news24.favoriteNews.FavoriteNewsFragment
import com.news.news24.listNews.ListNewsFragment
import com.news.news24.searchNews.SearchNewsFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigationView()
        navigateToPage(R.id.page_1)

    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            navigateToPage(menuItem.itemId)
            true
        }
    }

    private fun navigateToPage(itemId: Int) {
        val fragment: Fragment = when (itemId) {
            R.id.page_1 -> ListNewsFragment()
            R.id.page_2 -> SearchNewsFragment()
            R.id.page_3 -> FavoriteNewsFragment()
            else -> ListNewsFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .commit()
    }
}