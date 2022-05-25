package com.taijoo.cookingassistance.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.data.model.SearchCategoryData
import com.taijoo.cookingassistance.databinding.CustomCategoryDialogLayoutBinding

class CustomCategoryDialog(val activity: Activity , val item : List<SearchCategoryData>) : Dialog(activity) {

    private lateinit var binding : CustomCategoryDialogLayoutBinding
    private lateinit var customCategoryDialogListener : CustomCategoryDialogListener

    private lateinit var adapter: CustomCategoryAutoCompleteAdapter


    interface CustomCategoryDialogListener{
        fun onOkClick(type : Int)
        fun onNoClick()
    }

    fun setOnCategoryListener(customCategoryDialogListener : CustomCategoryDialogListener){
        this.customCategoryDialogListener = customCategoryDialogListener
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = DataBindingUtil.inflate(LayoutInflater.from(activity),R.layout.custom_category_dialog_layout,null,false)

        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
        val width = (context.resources.displayMetrics.widthPixels * 0.80).toInt()
//
        window!!.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        adapter = CustomCategoryAutoCompleteAdapter(activity,item)

        binding.spinner.adapter = adapter

        binding.tvOk.setOnClickListener{
            customCategoryDialogListener.onOkClick(binding.spinner.selectedItemPosition)
            dismiss()
        }

        binding.tvNo.setOnClickListener {
            customCategoryDialogListener.onNoClick()
            cancel()
        }
    }
}