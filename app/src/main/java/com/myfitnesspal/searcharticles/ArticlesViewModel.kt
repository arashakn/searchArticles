package com.myfitnesspal.searcharticles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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
    val curSearch = MutableLiveData<String>()

    private val compositeDisposable  = CompositeDisposable ()

    fun fetchArticles(q : String?, page : String = "0" ){
        curSearch.value = q

        if(q== null || q.length<2){
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


    fun fetchMoreArticles (page : String = "0" ){
        val q : String? = curSearch.value
        if(q== null || q.length<2){
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
                            val curList = articlesList.value
                            curList?.addAll(value.response.docs)
                            articlesList.value = curList
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


    var revrList  = Transformations.switchMap(articlesList ){
        val articlesList =  MutableLiveData<ArrayList<Article>>()
        it.reverse()
        articlesList.value?.addAll(it)
        articlesList
    }


}