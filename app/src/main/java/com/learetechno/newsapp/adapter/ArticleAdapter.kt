package com.learetechno.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.learetechno.newsapp.R
import com.learetechno.newsapp.model.articles.Article
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_headline.view.*

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {


    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.description == newItem.description
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    inner class ArticleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_headline, parent, false))
    }

    override fun getItemCount(): Int = differ.currentList.size
    private var onItemClickListener : ((Article) -> Unit)? = null
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(imgNews)
            titleNews.text = article.title
            setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }

    fun setOnItemClickListener(listener : (Article) -> Unit){
        onItemClickListener = listener
    }

}