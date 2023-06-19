package com.news.news24

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.core.data.Resource
import com.news.core.ui.NewsAdapter
import com.news.news24.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var activeTextView: TextView

    private lateinit var mainViewModel: MainViewModel

    private val newsAdapter = NewsAdapter()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)



        newsAdapter.onItemClick = { selectedData ->
//            val intent = Intent(activity, DetailTourismActivity::class.java)
//            intent.putExtra(DetailTourismActivity.EXTRA_DATA, selectedData)
//            startActivity(intent)
        }



        mainViewModel.getDataNews("news")
        getRequest()


        with(binding.listNews) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }


        binding.apply {
            textForYou.setOnClickListener {
                setActive(textForYou)
                mainViewModel.getDataNews("news")
                getRequest()

            }
            textSport.setOnClickListener {
                setActive(textSport)
                mainViewModel.getDataNews("sport")
                getRequest()

            }
            textPolitics.setOnClickListener {
                setActive(textPolitics)
                mainViewModel.getDataNews("politics")
                getRequest()
            }
            textWorld.setOnClickListener {
                setActive(textWorld)
                mainViewModel.getDataNews("world")
                getRequest()
            }
            textCrypto.setOnClickListener {
                setActive(textCrypto)
                mainViewModel.getDataNews("crypto")
                getRequest()
            }
            textBusiness.setOnClickListener {
                setActive(textBusiness)
                mainViewModel.getDataNews("business")
                getRequest()
            }
        }

        activeTextView = binding.textForYou
        activeTextView.isSelected = true
    }

    private fun getRequest(){
        mainViewModel.newsData.observe(this) { resource ->
            if (resource != null) {
                when (resource) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Log.i("resource", resource.data?.size.toString())
                        newsAdapter.setDataNews(resource.data!!)

                    }

                    is Resource.Error -> {
                        Log.i("jancok","MASUK COK")
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            resource.message ?: getString(R.string.wrong_message)
                    }
                }
            }
        }
    }

    private fun setActive(textView: TextView) {
        activeTextView.isSelected = false
        activeTextView = textView
        activeTextView.isSelected = true
    }
}