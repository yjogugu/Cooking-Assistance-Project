package com.taijoo.cookingassistance.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.databinding.CustomDateDialogLayoutBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class CustomDateDialog(private val activity: Context , private val title : String  ,private val oldDate : String) : Dialog(activity) {

    private lateinit var customCategoryDialogListener : CustomCategoryDialogListener

    private lateinit var binding : CustomDateDialogLayoutBinding

    private val format : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd",Locale.KOREA)

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

        binding.apply {
            titleTextview = title
            if(oldDate != "0000-00-00"){//날짜가 이미 지정되었을때만 지정된 날짜로 시작
                val calendar = Calendar.getInstance()

                var date : Date = Date()

                try {
                    date = format.parse(oldDate)!!
                }catch (e : Exception){

                }
                calendar.time = date

                datePicker.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY),calendar.get(Calendar.DAY_OF_MONTH))
            }
        }

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

            val strDate = format.format(calendar.time).toString()

            customCategoryDialogListener.onOkClick(strDate)
            dismiss()
        }
    }


}