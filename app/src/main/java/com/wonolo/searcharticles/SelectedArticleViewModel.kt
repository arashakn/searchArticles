package com.wonolo.searcharticles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wonolo.nytimesseach.model.Article

class SelectedArticleViewModel : ViewModel() {
    val selectedArticle = MutableLiveData<Article>()
}