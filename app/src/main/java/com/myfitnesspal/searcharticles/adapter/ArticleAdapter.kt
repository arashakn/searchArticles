package com.myfitnesspal.nytimesseach.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myfitnesspal.nytimesseach.model.Article
import com.myfitnesspal.nytimesseach.network.ArticlesAPIClient
import com.myfitnesspal.searcharticles.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_list_item.view.*


class ArticleAdapter(val onArticleClickListener :OnArticleClickListener ) :
    ListAdapter<Article,ArticleAdapter.ArticleViewHolder>(ArticleDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_list_item,parent,false)
        return ArticleViewHolder(view, onArticleClickListener)
    }


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }


    inner class ArticleViewHolder(val view : View , val onclick : OnArticleClickListener): RecyclerView.ViewHolder(view){
        fun onBind( article: Article ){
            view.setOnClickListener { onclick.onArticleClick(getItem(adapterPosition)) }
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


/**
 * DiffUtil is a utility class that can calculate the difference between two lists
 * and output a list of update operations that converts the first list into the second one.
 */
class ArticleDiffCallBack : DiffUtil.ItemCallback<Article>(){
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.web_url == newItem.web_url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem // equality check , tells DiffUtil the item content has been changed
    }

}