package com.taijoo.cookingassistance.view.cookinglist

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.taijoo.cookingassistance.data.model.CookingListData
import com.taijoo.cookingassistance.data.model.CookingListResponse
import com.taijoo.cookingassistance.databinding.ListItemTestBinding

class CookingListViewHolder(private val binding : ListItemTestBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data : CookingListData){
        binding.apply {
            this.name = data.name
            executePendingBindings()
        }
    }
}