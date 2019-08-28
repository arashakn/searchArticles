package com.wonolo.nytimesseach.network

import com.wonolo.nytimesseach.model.Articles
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesAPI {
    @GET("svc/search/v2/articlesearch.json")
    fun getArticles(@Query("q") query : String , @Query("api-key") key :String =ArticlesAPIClient.API_KEY  ) : Single<Articles>

}