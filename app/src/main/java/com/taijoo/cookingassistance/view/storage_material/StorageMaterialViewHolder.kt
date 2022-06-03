package com.taijoo.cookingassistance.view.storage_material

import androidx.recyclerview.widget.RecyclerView
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.databinding.ListItemMaterialBinding

class StorageMaterialViewHolder(private val binding : ListItemMaterialBinding , private val storageMaterialInterface : StorageMaterialInterface) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item : StorageMaterialData){
        binding.apply {
            storageMaterialData = item
            executePendingBindings()
        }

        binding.constraint.setOnClickListener {
            storageMaterialInterface.itemClick(item)
        }

    }
}