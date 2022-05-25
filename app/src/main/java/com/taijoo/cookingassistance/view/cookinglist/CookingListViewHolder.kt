package com.taijoo.cookingassistance.view.cookinglist

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.util.Log
import android.util.Pair
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taijoo.cookingassistance.data.model.CookingListData
import com.taijoo.cookingassistance.data.model.CookingListResponse
import com.taijoo.cookingassistance.databinding.ListItemCookingBinding
import com.taijoo.cookingassistance.util.IP
import com.taijoo.cookingassistance.view.cooking_recipe.CookingRecipeActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext

class CookingListViewHolder(private val binding : ListItemCookingBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data : CookingListData , onItemClickListener : CookingListAdapter.OnItemClickListener){

        binding.apply {
            this.data = data
            executePendingBindings()
        }

        binding.constraint.setOnClickListener { view->
            Log.e("여기","ㅇㅇ"+data.img)
            onItemClickListener.onItemClick(view, data)
        }

    }

}