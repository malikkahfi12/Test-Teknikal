package com.learetechno.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.learetechno.newsapp.R
import com.learetechno.newsapp.databinding.LayoutSourceBinding
import com.learetechno.newsapp.model.source.Source
import kotlinx.android.synthetic.main.layout_source.view.*

class SourceAdapter : RecyclerView.Adapter<SourceAdapter.SourceViewHolder>() {

    inner class  SourceViewHolder(val binding : LayoutSourceBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Source>(){
        override fun areItemsTheSame(oldItem: Source, newItem: Source): Boolean {
            return oldItem.description == newItem.description
        }

        override fun areContentsTheSame(oldItem: Source, newItem: Source): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val layoutDataBinding : LayoutSourceBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_source, parent, false)
        return SourceViewHolder(layoutDataBinding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener : ((Source) -> Unit)? = null

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {

        val source = differ.currentList[position]
        holder.binding.model = source
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let {
                    it(source)
                }
            }
        }
    }

    fun setOnItemClickListener(listener : (Source) -> Unit){
        onItemClickListener = listener
    }

}