package com.hadirahimi.worldnews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadirahimi.worldnews.data.model.NewsResponse
import com.hadirahimi.worldnews.data.repository.MainRepository
import com.hadirahimi.worldnews.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository : MainRepository) : ViewModel()
{
    var newsData = MutableLiveData<MyResponse<NewsResponse>>()
    fun getAllNotes() = viewModelScope.launch {
        repository.lastNews().collect{
            newsData.postValue(it)
        }
    }
}









