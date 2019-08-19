package com.myfitnesspal.searcharticles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myfitnesspal.nytimesseach.model.Article

class SelectedArticleViewModel : ViewModel() {
    val selectedArticle = MutableLiveData<Article>()
}