package com.example.ecommerce.utils.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.databinding.LoaderBinding

class LoaderAdapter : LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    class LoaderViewHolder(val binding: LoaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState){
            binding.progressBar.isVisible = loadState is LoadState.Loading
        }

    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val binding = LoaderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoaderViewHolder(binding)
    }
}