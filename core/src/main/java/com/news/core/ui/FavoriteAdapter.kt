package com.news.core.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.news.core.R
import com.news.core.databinding.ItemNewsBinding
import com.news.core.domain.model.News

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    private var listDataNews = ArrayList<News>()

    var onItemClick: ((News) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setDataNews(newListData: List<News>) {
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
        fun bind(data: News) {
            with(binding) {
                if(data.urlToImage != "no urlImage"){
                    Glide.with(itemView.context)
                        .load(data.urlToImage)
                        .into(itemImage)
                }else itemImage.setImageResource(R.drawable.logo)

                itemTitle.text = data.title
                itemAuthor.text = data.author
            }
        }

        init {
            binding.root.setOnClickListener {

                onItemClick?.invoke(listDataNews[adapterPosition])
            }
        }
    }
}