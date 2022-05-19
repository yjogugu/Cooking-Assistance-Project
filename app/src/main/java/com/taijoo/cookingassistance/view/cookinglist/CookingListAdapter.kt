package com.taijoo.cookingassistance.view.cookinglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.taijoo.cookingassistance.data.model.CookingListData
import com.taijoo.cookingassistance.data.model.CookingListResponse
import com.taijoo.cookingassistance.databinding.ListItemTestBinding

class CookingListAdapter : PagingDataAdapter<CookingListData, CookingListViewHolder>(CookingListDiffCallback()){

    private class CookingListDiffCallback : DiffUtil.ItemCallback<CookingListData>() {

        override fun areItemsTheSame(oldItem: CookingListData, newItem: CookingListData): Boolean {
            return oldItem.seq == newItem.seq
        }

        override fun areContentsTheSame(
            oldItem: CookingListData,
            newItem: CookingListData
        ): Boolean {
            return oldItem.seq == newItem.seq
        }
    }

    override fun onBindViewHolder(holder: CookingListViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookingListViewHolder {
        return CookingListViewHolder(ListItemTestBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}