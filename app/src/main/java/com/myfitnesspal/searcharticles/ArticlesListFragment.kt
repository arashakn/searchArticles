package com.myfitnesspal.searcharticles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.myfitnesspal.nytimesseach.adapter.ArticleAdapter
import com.myfitnesspal.nytimesseach.model.Article
import kotlinx.android.synthetic.main.articles_list.*

class ArticlesListFragment : Fragment(), ArticleAdapter.OnArticleClickListener{

    private lateinit var articleAdapter : ArticleAdapter
    lateinit var articlesViewModel: ArticlesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.articles_list,container,false)
        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        articlesViewModel = ViewModelProviders.of(this).get(ArticlesViewModel::class.java)
        articleAdapter = ArticleAdapter(onArticleClickListener = this)
        rv_articles.adapter = articleAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        rv_articles.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(context,linearLayoutManager.getOrientation())
        rv_articles.addItemDecoration(dividerItemDecoration)
        rv_articles.setHasFixedSize(true)
        search.isIconified = false
        search.clearFocus()
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                articlesViewModel.fetchArticles(query.trim())
                return false
            }

            override fun onQueryTextChange(text: String): Boolean {
                return false
            }
        })

        observeViewModel()
    }

    private  fun observeViewModel(){
        articlesViewModel.articlesList.observe(this, Observer {
            it?.let {

                if(it.isEmpty()){
                    rv_articles.visibility = View.GONE
                    tv_error.visibility = View.VISIBLE
                    tv_error.text =getResources().getString(R.string.error_message_empty_result);
                }
                else{
                    rv_articles.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    articleAdapter.addArticles(it)
                }

            } ?:run{
                rv_articles.visibility = View.GONE
                tv_error.visibility = View.VISIBLE
            }
        })

        articlesViewModel.error.observe(this, Observer {
            when(it){
                true ->
                {
                    rv_articles.visibility = View.GONE
                    tv_error.visibility = View.VISIBLE
                }

                false ->
                {
                    rv_articles.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                }
            }

        })
        articlesViewModel.progress.observe(this, Observer {
            when(it){
                true  -> {
                    progress_circular.visibility = View.VISIBLE
                    rv_articles.visibility = View.GONE
                }
                false -> progress_circular.visibility = View.GONE
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onArticleClick(article : Article) {
        activity?.let {
            val selectedArticleViewModel = ViewModelProviders.of(it).get(SelectedArticleViewModel::class.java)//makes the ViewModel scoop to activity rather than fragment
            selectedArticleViewModel.selectedArticle.value = article
            it.supportFragmentManager.beginTransaction().replace(R.id.screen_container ,
                ArticleDetailsFragment()
            )
                .addToBackStack(null)
                .commit()
        }
    }
}