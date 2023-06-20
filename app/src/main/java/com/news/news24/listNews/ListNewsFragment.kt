package com.news.news24.listNews

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.news.core.data.Resource
import com.news.core.ui.NewsAdapter
import com.news.news24.R
import com.news.news24.databinding.FragmentListNewsBinding
import com.news.news24.detailsNews.DetailsNewsActivity
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class ListNewsFragment : Fragment() {
    private var _binding: FragmentListNewsBinding? = null
    private val binding get() = _binding!!
    private val listNewsViewModel:ListNewsViewModel by viewModels()

    private lateinit var activeTextView: TextView

    private val newsAdapter = NewsAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            listNewsViewModel.getDataNews("news")
            getRequest()

            binding.apply {
                textForYou.setOnClickListener {
                    setActive(textForYou)
                    listNewsViewModel.getDataNews("news")
                    getRequest()

                }
                textSport.setOnClickListener {
                    setActive(textSport)
                    listNewsViewModel.getDataNews("sport")
                    getRequest()

                }
                textPolitics.setOnClickListener {
                    setActive(textPolitics)
                    listNewsViewModel.getDataNews("politics")
                    getRequest()
                }
                textWorld.setOnClickListener {
                    setActive(textWorld)
                    listNewsViewModel.getDataNews("world")
                    getRequest()
                }
                textCrypto.setOnClickListener {
                    setActive(textCrypto)
                    listNewsViewModel.getDataNews("crypto")
                    getRequest()
                }
                textBusiness.setOnClickListener {
                    setActive(textBusiness)
                    listNewsViewModel.getDataNews("business")
                    getRequest()
                }
            }

            activeTextView = binding.textForYou
            activeTextView.isSelected = true

            newsAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailsNewsActivity::class.java)
                intent.putExtra(DetailsNewsActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            with(binding.listNews) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = newsAdapter
            }
        }
    }

    private fun getRequest(){
        listNewsViewModel.newsData.observe(requireActivity()) { resource ->
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}