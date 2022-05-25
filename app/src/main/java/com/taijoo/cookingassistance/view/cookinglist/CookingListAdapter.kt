package com.taijoo.cookingassistance.view.cookinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.taijoo.cookingassistance.data.model.CookingListData
import com.taijoo.cookingassistance.data.model.CookingListResponse
import com.taijoo.cookingassistance.databinding.ListItemCookingBinding


class CookingListAdapter : PagingDataAdapter<CookingListData, CookingListViewHolder>(CookingListDiffCallback()){

    private var onItemClickListener : OnItemClickListener? = null

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

    fun setOnItemClickListener(onItemClickListener : OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener{
        fun onItemClick(view : View , data : CookingListData)
    }

    override fun onBindViewHolder(holder: CookingListViewHolder, position: Int) {
        holder.bind(getItem(position)!!,onItemClickListener!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookingListViewHolder {
        return CookingListViewHolder(ListItemCookingBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}