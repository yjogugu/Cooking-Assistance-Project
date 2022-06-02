package com.taijoo.cookingassistance.view.cooking_recipe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taijoo.cookingassistance.data.model.CookingRecipeData
import com.taijoo.cookingassistance.databinding.ListItemCookingRecipeBinding

class CookingRecipeAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var item : List<CookingRecipeData> = ArrayList()


    @SuppressLint("NotifyDataSetChanged")
    fun setData(item : List<CookingRecipeData>){
        this.item = item
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CookingRecipeViewHolder(ListItemCookingRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CookingRecipeViewHolder){
            holder.bind(item[position])
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }

}