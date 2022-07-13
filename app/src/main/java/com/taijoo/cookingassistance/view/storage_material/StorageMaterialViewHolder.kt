package com.taijoo.cookingassistance.view.storage_material

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.databinding.ListItemMaterialBinding

class StorageMaterialViewHolder(private val binding : ListItemMaterialBinding , private val storageMaterialInterface : StorageMaterialInterface) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item : StorageMaterialData){
        binding.apply {
            storageMaterialData = item
            executePendingBindings()
        }

//        if(item.deleteType == 1){
//            binding.constraint.visibility = View.GONE
//        }
//        else{
//            if(item.seq.toInt() == 20){
//                binding.constraint.visibility = View.VISIBLE
//            }
//            else{
//                binding.constraint.visibility = View.GONE
//            }
//
//
//        }

        binding.constraint.setOnClickListener {
            storageMaterialInterface.itemClick(item,bindingAdapterPosition)
        }

    }
}