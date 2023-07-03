package com.hadirahimi.worldnews.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadirahimi.worldnews.R
import com.hadirahimi.worldnews.databinding.ActivityMainBinding
import com.hadirahimi.worldnews.utils.MyResponse
import com.hadirahimi.worldnews.utils.MyResponse.Status.*
import com.hadirahimi.worldnews.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    private var _binding : ActivityMainBinding?=null
    private val binding get() = _binding
    
    private val viewModel : MainViewModel by viewModels()
    
    @Inject
    lateinit var newsAdapter : AdapterNews
    
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupViews()
        observeNewsData()
        viewModel.getAllNotes()
    }
    
    private fun observeNewsData()
    {
        viewModel.newsData.observe(this@MainActivity){response ->
            when(response.status)
            {
                LOADING -> {
                    binding?.loading?.visibility = View.VISIBLE
                }
                SUCCESS -> {
                    binding?.loading?.visibility = View.GONE
                    response?.data?.articles?.let { newsAdapter.submitData(it) }
                }
                ERROR -> {
                    binding?.loading?.visibility = View.GONE
                    Toast.makeText(this@MainActivity , response.message , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun setupViews()
    {
        binding?.recyclerview?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
            adapter = newsAdapter
        }
    }
    
    override fun onDestroy()
    {
        super.onDestroy()
        _binding = null
    }
}













