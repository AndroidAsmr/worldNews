package com.hadirahimi.worldnews.data.server

import com.hadirahimi.worldnews.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService
{
    @GET("top-headlines")
    suspend fun getTopHeadLines(
        @Query("apiKey") apiKey : String ,
        @Query("country") country : String
    ) : Response<NewsResponse>
}