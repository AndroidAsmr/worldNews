package com.hadirahimi.worldnews.data.repository

import com.hadirahimi.worldnews.data.model.NewsResponse
import com.hadirahimi.worldnews.data.server.NewsApiService
import com.hadirahimi.worldnews.utils.MyResponse
import com.hadirahimi.worldnews.utils.TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(private val api : NewsApiService)
{
    suspend fun lastNews() : Flow<MyResponse<NewsResponse>> = flow {
        emit(MyResponse.loading())
        val response = api.getTopHeadLines(TOKEN,"us")
        if (response.isSuccessful)
            emit(MyResponse.success(response.body()))
        else emit(MyResponse.error("please try again later!"))
    }.catch {
        emit(MyResponse.error(it.message.toString()))
    }
}












