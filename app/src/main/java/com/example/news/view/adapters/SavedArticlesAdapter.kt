package com.example.news.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.databinding.ItemSavedBinding
import com.example.news.model.Article

class SavedArticlesAdapter : RecyclerView.Adapter<SavedArticlesAdapter.SavedArticlesViewHolder>() {


    private val diffUtilCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class SavedArticlesViewHolder(var view: ItemSavedBinding) :
        RecyclerView.ViewHolder(view.root)

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedArticlesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemSavedBinding>(
            inflater,
            R.layout.item_saved,
            parent,
            false
        )
        return SavedArticlesViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: SavedArticlesViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.view.article = article

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                article.let { article ->
                    it(article)
                }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}