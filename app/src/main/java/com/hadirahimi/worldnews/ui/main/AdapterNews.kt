package com.hadirahimi.worldnews.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hadirahimi.worldnews.R
import com.hadirahimi.worldnews.data.model.Article
import com.hadirahimi.worldnews.databinding.ItemNewsBinding
import dagger.hilt.android.qualifiers.ActivityContext
import java.lang.IllegalStateException
import javax.inject.Inject

class AdapterNews @Inject constructor(@ActivityContext private val context : Context):RecyclerView.Adapter<AdapterNews.MyViewHolder>()
{
    private lateinit var binding : ItemNewsBinding
    private var newsList = emptyList<Article>()
    
    inner class MyViewHolder : RecyclerView.ViewHolder(binding.root)
    {
        fun setData(data:Article)
        {
            binding.apply {
                newsTitle.text = data.title
                newsDescription.text = shortenString(data.description)
                Glide.with(context)
                    .load(data.imageUrl)
                    .error(R.drawable.ic_news)
                    .placeholder(R.drawable.ic_news)
                    .into(newsImage)
            }
        }
    }
    fun shortenString(input : String?):String{
        if (input != null){
            return if (input.length >100)
            {
                val shortenedText = input.substring(0,100)
                val lastSpaceIndex = shortenedText.lastIndexOf(' ')
                if (lastSpaceIndex != -1)
                {
                    shortenedText.substring(0,lastSpaceIndex)+" ..."
                }else {
                    "$shortenedText ..."
                }
            }else{
                input
            }
        }else {
            return ""
        }
    }
    
    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : MyViewHolder
    {
        binding = ItemNewsBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder()
    }
    
    override fun getItemCount() : Int
    {
        return newsList.size
    }
    
    override fun getItemViewType(position : Int) : Int
    {
        return position
    }
    
    override fun onBindViewHolder(holder : MyViewHolder , position : Int)
    {
        holder.setData(newsList[position])
    }
    fun submitData(data : List<Article>)
    {
        val newsDiffUtil = NewsDiffUtils(newsList,data)
        val diffutils = DiffUtil.calculateDiff(newsDiffUtil)
        newsList = data
        diffutils.dispatchUpdatesTo(this)
    }
    class NewsDiffUtils(
        private val oldItem : List<Article>,
        private val newItem : List<Article>
    ) : DiffUtil.Callback()
    {
        override fun getOldListSize() : Int
        {
            return oldItem.size
        }
    
        override fun getNewListSize() : Int
        {
            return newItem.size
        }
    
        override fun areItemsTheSame(oldItemPosition : Int , newItemPosition : Int) : Boolean
        {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }
    
        override fun areContentsTheSame(oldItemPosition : Int , newItemPosition : Int) : Boolean
        {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }
    }
}






