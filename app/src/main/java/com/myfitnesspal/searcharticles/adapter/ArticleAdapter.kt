package com.myfitnesspal.nytimesseach.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myfitnesspal.nytimesseach.model.Article
import com.myfitnesspal.nytimesseach.network.ArticlesAPIClient
import com.myfitnesspal.searcharticles.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_list_item.view.*

class ArticleAdapter(val articles : ArrayList<Article> = ArrayList<Article>(), val onArticleClickListener :OnArticleClickListener ) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_list_item,parent,false)
        return ArticleViewHolder(view, onArticleClickListener)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun addArticles(list : ArrayList<Article>){
        articles.clear()
        articles.addAll(list)
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(val view : View , val onclick : OnArticleClickListener): RecyclerView.ViewHolder(view){
        fun onBind( position: Int ){
            val article = articles[position]
            view.setOnClickListener { onclick.onArticleClick(articles[adapterPosition]) }
            view.tv_title.text = article.headline.main
            view.iv_article.setImageDrawable(null)
            val multimedia = article.multimedia.filter { it.subtype.equals("thumbnail") }.firstOrNull()
            multimedia?.url?.let {
                val imageURL = ArticlesAPIClient.BASE_IMAGE_URL +"/" +it
                Picasso.with(view.iv_article.getContext().getApplicationContext())
                    .load(imageURL)
                    .fit()
                    .centerCrop()
                    .into(view.iv_article)
            }
        }
    }
    interface OnArticleClickListener {
        fun onArticleClick(article : Article)
    }
}