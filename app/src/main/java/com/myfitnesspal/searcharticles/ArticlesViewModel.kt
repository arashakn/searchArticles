package com.myfitnesspal.searcharticles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myfitnesspal.nytimesseach.model.Article
import com.myfitnesspal.nytimesseach.model.Articles
import com.myfitnesspal.nytimesseach.network.ArticlesAPIClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.lang.RuntimeException

class ArticlesViewModel : ViewModel() {
    val articlesList =  MutableLiveData<ArrayList<Article>>()
    val error = MutableLiveData<Boolean>()
    val progress = MutableLiveData<Boolean>()
    private val compositeDisposable  = CompositeDisposable ()

    fun fetchArticles(q : String ){
        if(q.length<2){
            return
        }
        error.value = false
        progress.value = true

        try {
            val disposable = ArticlesAPIClient.getArticleService().getArticles(query = q.trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object  : DisposableSingleObserver<Articles>(){
                    override fun onSuccess(value: Articles?) {
                        value?.response?.docs?.let {
                            articlesList.value = value.response.docs
                        }
                        progress.value = false
                    }
                    override fun onError(e: Throwable?) {
                        error.value = true
                        progress.value = false
                    }
                })
            compositeDisposable.add(disposable)
        }catch (e : RuntimeException){
            error.value = true
            progress.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
            compositeDisposable.clear()

    }

}