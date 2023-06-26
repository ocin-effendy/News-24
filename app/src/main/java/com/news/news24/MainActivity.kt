package com.news.news24

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.news.news24.databinding.ActivityMainBinding
import com.news.news24.listNews.ListNewsFragment
import com.news.news24.searchNews.SearchNewsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var broadcastReceiver: BroadcastReceiver

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigationView()
        navigateToPage(R.id.page_1)

    }

    private fun registerBroadCastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

            }
        }
        val intentFilter = IntentFilter()
        intentFilter.apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStart() {
        super.onStart()
        registerBroadCastReceiver()
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
            R.id.page_3 -> loadFragmentFromDynamicFeature()
            else -> ListNewsFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .commit()
    }

    private fun loadFragmentFromDynamicFeature(): Fragment {
        val moduleClassName = "com.news.favorite.FavoriteFragment"
        val moduleClassLoader = classLoader
        return moduleClassLoader.loadClass(moduleClassName).newInstance() as Fragment
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
}