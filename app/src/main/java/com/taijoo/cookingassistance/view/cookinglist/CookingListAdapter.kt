package com.taijoo.cookingassistance.view.cookinglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.taijoo.cookingassistance.databinding.ListItemTestBinding

class CookingListAdapter : PagingDataAdapter<String , CookingListViewHolder>(CookingListDiffCallback()){

    private class CookingListDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: CookingListViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookingListViewHolder {
        return CookingListViewHolder(ListItemTestBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}