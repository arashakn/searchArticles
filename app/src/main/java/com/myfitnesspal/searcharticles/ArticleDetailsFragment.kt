package com.myfitnesspal.searcharticles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.article_details_view.*
import kotlinx.android.synthetic.main.article_list_item.tv_title

class ArticleDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.article_details_view, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        displayArticle()
    }

    private  fun displayArticle(){

        activity?.let {
            val selectedArticleViewModel = ViewModelProviders.of(it).get(SelectedArticleViewModel::class.java)//makes the ViewModel scoop to activity rather than fragment
            selectedArticleViewModel.selectedArticle.observe(this, Observer {
                it?.let {
                    val subject =it.headline.main
                    val webUrl: String? = it.web_url

                    tv_title.text = subject
                    tv_lead_paragraph.text = it.lead_paragraph
                    tv_snippet.text = it.snippet

                    btn_share.setOnClickListener {
                        webUrl?.let {
                            val i = Intent(Intent.ACTION_SEND)
                            i.type = "text/plain"
                            i.putExtra(Intent.EXTRA_SUBJECT,subject)
                            i.putExtra(Intent.EXTRA_TEXT,webUrl )
                            startActivity(Intent.createChooser(i, "Share URL"))
                        }

                    }
                }
            }
            )
        }
    }
}