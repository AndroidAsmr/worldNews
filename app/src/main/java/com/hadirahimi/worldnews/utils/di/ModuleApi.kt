package com.hadirahimi.worldnews.utils.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hadirahimi.worldnews.data.server.NewsApiService
import com.hadirahimi.worldnews.utils.BASE_URL
import com.hadirahimi.worldnews.utils.TIMEOUT_TIME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleApi
{
    @Provides
    @Singleton
    fun provideUrl() = BASE_URL
    
    @Provides
    @Singleton
    fun provideGson() : Gson = GsonBuilder().setLenient().create()
    
    @Provides
    @Singleton
    fun provideTimeOut() = TIMEOUT_TIME
    
    @Provides
    @Singleton
    fun provideClient(time : Long) = OkHttpClient.Builder()
        .connectTimeout(time,TimeUnit.SECONDS)
        .readTimeout(time,TimeUnit.SECONDS)
        .writeTimeout(time,TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()
    
    @Provides
    @Singleton
    fun provideRetrofit(base_url : String, gson:Gson,client : OkHttpClient) : NewsApiService =
        Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create(gson))
            .client(client).build().create(NewsApiService::class.java)
}










