package com.taijoo.cookingassistance.view.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.data.model.MaterialData
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.databinding.ListItemSearchBinding

class SearchAdapter(var searchInterface : SearchInterface) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var item : List<StorageMaterialData> = ArrayList()


    @SuppressLint("NotifyDataSetChanged")
    fun setData(item: List<StorageMaterialData>){
        this.item = item
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_search , parent , false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is SearchViewHolder){
            holder.bind(item[position])
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }


    private inner class SearchViewHolder(var binding : ListItemSearchBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : StorageMaterialData){
            binding.apply {
                data = item
                executePendingBindings()
            }

            binding.btAdd.setOnClickListener {
                item.size += 1
                binding.textView5.text = item.size.toString()
                searchInterface.onItemClick(item)
            }

            binding.btDelete.setOnClickListener {
                if(item.size > 0){
                    item.size -= 1
                    binding.textView5.text = item.size.toString()
                    searchInterface.onItemClick(item)
                }

            }


        }

    }
}