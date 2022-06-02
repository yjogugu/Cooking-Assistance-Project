package com.taijoo.cookingassistance.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.databinding.CustomDateDialogLayoutBinding
import java.text.SimpleDateFormat
import java.util.*

class CustomDateDialog(private val activity: Context) : Dialog(activity) {

    private lateinit var customCategoryDialogListener : CustomCategoryDialogListener

    private lateinit var binding : CustomDateDialogLayoutBinding


    interface CustomCategoryDialogListener{
        fun onOkClick(date : String)
        fun onNoClick()
    }

    fun setOnClickListener(customCategoryDialogListener : CustomCategoryDialogListener){
        this.customCategoryDialogListener = customCategoryDialogListener
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.custom_date_dialog_layout,null,false)

        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val width = (context.resources.displayMetrics.widthPixels * 0.80).toInt()

        window!!.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)


        binding.tvNo.setOnClickListener {
            customCategoryDialogListener.onNoClick()
            dismiss()
        }

        binding.tvOk.setOnClickListener {
            val year = binding.datePicker.year
            val month = binding.datePicker.month
            val day = binding.datePicker.dayOfMonth


            val calendar = Calendar.getInstance()
            calendar[year, month] = day

            val format = SimpleDateFormat("yyyy-MM-dd",Locale.KOREA)
            val strDate = format.format(calendar.time).toString()

            customCategoryDialogListener.onOkClick(strDate)
            dismiss()
        }
    }


}