package com.news.news24.searchNews

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.news.core.data.Resource
import com.news.core.ui.NewsAdapter
import com.news.news24.R
import com.news.news24.databinding.FragmentSearchNewsBinding
import com.news.news24.detailsNews.DetailsNewsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchNewsFragment : Fragment() {
    private var searchJob: Job? = null
    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!

    private val searchNewsViewModel: SearchNewsViewModel by viewModels()
    private val newsAdapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null){

            searchNewsViewModel.getDataNews("2023")
            setDataList()

            binding.searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.i("search", s.toString())
                    searchJob?.cancel()
                    searchJob = CoroutineScope(Dispatchers.Main).launch {
                        delay(500) // Delay 500ms (debouncing)
                        val query = s.toString()
                        searchNewsViewModel.getDataNews(query)
                        setDataList()

                    }
                }
                override fun afterTextChanged(s: Editable?) {
                }
            })

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

    private fun setDataList(){
        searchNewsViewModel.newsData.observe(requireActivity()) { resource ->
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}