package com.news.core.ui

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.DiffUtil



class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ListViewHolder>() {

    private var listDataNews = ArrayList<ArticlesItem>()

    var onItemClick: ((News) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setDataNews(newListData: List<ArticlesItem>) {
        val diffResult = DiffUtil.calculateDiff(
            NewsDiffCallback(
                listDataNews,
                newListData
            )
        )
        listDataNews.clear()
        listDataNews.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
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
            binding.root.setOnClickListener {
                val news = News(
                    author = listDataNews[adapterPosition].author ?: "no author",
                    title = listDataNews[adapterPosition].title ?: "no title",
                    description = listDataNews[adapterPosition].description ?: "no desctiption",
                    url = listDataNews[adapterPosition].url ?: "no url",
                    urlToImage = listDataNews[adapterPosition].urlToImage ?: "no urlImage",
                    publishedAt = listDataNews[adapterPosition].publishedAt ?: "no published",
                    content = listDataNews[adapterPosition].content ?: "no content",
                )
                onItemClick?.invoke(news)
            }
        }
    }

    class NewsDiffCallback(private val oldList: List<ArticlesItem>, private val newList: List<ArticlesItem>) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].title == newList[newItemPosition].title

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}