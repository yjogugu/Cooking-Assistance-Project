package com.taijoo.cookingassistance.view.cooking_recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taijoo.cookingassistance.databinding.ListItemCookingRecipeBinding

class CookingRecipeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var item = ArrayList<String>()

    fun setData(item : ArrayList<String>){
        this.item = item
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CookingRecipeViewHolder(ListItemCookingRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return item.size
    }

}