package com.taijoo.cookingassistance.view.cooking_recipe

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.taijoo.cookingassistance.data.model.CookingRecipeData
import com.taijoo.cookingassistance.databinding.ListItemCookingRecipeBinding

class CookingRecipeViewHolder(private val binding : ListItemCookingRecipeBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data : CookingRecipeData){
        binding.apply {
            this.data = data
            executePendingBindings()
        }
    }
}