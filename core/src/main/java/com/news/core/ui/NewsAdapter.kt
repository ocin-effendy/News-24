package com.news.core.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.news.core.databinding.ItemNewsBinding
import com.news.core.domain.model.News
import java.util.ArrayList
import com.news.core.R
import com.news.core.data.resource.remote.response.ArticlesItem
import kotlin.math.log

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ListViewHolder>() {

    private var listDataNews = ArrayList<ArticlesItem>()

    var onItemClick: ((ArticlesItem) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setDataNews(newListData: List<ArticlesItem>) {
        Log.i("setdata", newListData.toString())
        listDataNews.clear()
        listDataNews.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))

    override fun getItemCount() = listDataNews.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listDataNews[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNewsBinding.bind(itemView)
        fun bind(data: ArticlesItem) {
            with(binding) {
                if(data.urlToImage != null){
                    Glide.with(itemView.context)
                        .load(data.urlToImage)
                        .into(itemImage)
                }else itemImage.setImageResource(R.drawable.logo)

                itemTitle.text = data.title
                itemAuthor.text = data.author ?: "no Author"
            }
        }

        init {
            // perlu diperbaiki
            binding.root.setOnClickListener {
                onItemClick?.invoke(listDataNews[adapterPosition])
            }
        }
    }
}