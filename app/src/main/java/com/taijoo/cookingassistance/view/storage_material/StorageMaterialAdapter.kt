package com.taijoo.cookingassistance.view.storage_material

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.databinding.ListItemTestBinding

class StorageMaterialAdapter : PagingDataAdapter<StorageMaterialData , RecyclerView.ViewHolder>(DIFF) {

    companion object {
        private val DIFF = object: DiffUtil.ItemCallback<StorageMaterialData>() {
            override fun areItemsTheSame(oldItem: StorageMaterialData, newItem: StorageMaterialData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StorageMaterialData, newItem: StorageMaterialData): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StorageMaterialViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_material , parent , false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is StorageMaterialViewHolder){
            holder.bind(getItem(position)!!)
        }
    }



}