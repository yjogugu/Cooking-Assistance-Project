package com.taijoo.cookingassistance.view.cookinglist

import androidx.recyclerview.widget.RecyclerView
import com.taijoo.cookingassistance.databinding.ListItemTestBinding

class CookingListViewHolder(private val binding : ListItemTestBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(name : String){
        binding.apply {
            this.name = name
            executePendingBindings()
        }
    }
}