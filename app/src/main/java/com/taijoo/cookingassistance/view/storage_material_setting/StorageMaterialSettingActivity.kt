package com.taijoo.cookingassistance.view.storage_material_setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.databinding.ActivityStorageMaterialSettingBinding
import com.taijoo.cookingassistance.util.CustomDateDialog
import com.taijoo.cookingassistance.util.CustomDefaultDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StorageMaterialSettingActivity : AppCompatActivity() {

    private lateinit var binding : ActivityStorageMaterialSettingBinding

    private val viewModel : StorageMaterialSettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_storage_material_setting)

        binding.apply {
            viewmodel = viewModel
            titleAppbar.ivBack.visibility = View.VISIBLE
            titleAppbar.ivSearch.visibility = View.VISIBLE
            titleAppbar.ivSearch.setImageDrawable(AppCompatResources.getDrawable(this@StorageMaterialSettingActivity,R.drawable.ic_delete))
            viewModel.seq = intent.getLongExtra("seq",0)
            activity = this@StorageMaterialSettingActivity
            lifecycleOwner = this@StorageMaterialSettingActivity
        }

        viewModel.getData()


        binding.titleAppbar.ivSearch.setOnClickListener {
            val customDefaultDialog = CustomDefaultDialog(this,"재료삭제","재료를 삭제 하시겠습니까?")

            customDefaultDialog.setDialogListener(object : CustomDefaultDialog.CustomDialogListener{
                    override fun onCheckClick() {
                        viewModel.setDelete()
                        finish()
                    }

                    override fun onNoClick() {

                    }

                })

            customDefaultDialog.show()

        }
    }

    //유통기한 수정하기
    fun onClickExpirationDate(){
        binding.constraint.isFocusableInTouchMode = true
        binding.constraint.requestFocus()

        val customDateDialog = CustomDateDialog(this , viewModel.storageData.value.expiration_date)

        customDateDialog.setOnClickListener(object : CustomDateDialog.CustomCategoryDialogListener{
            override fun onOkClick(date : String) {
                viewModel.setExpirationDate(date)
            }

            override fun onNoClick() {

            }

        })

        customDateDialog.show()
    }

    //구입한 날짜 수정하기
    fun onClickDate(){
        binding.constraint.isFocusableInTouchMode = true
        binding.constraint.requestFocus()

        val customDateDialog = CustomDateDialog(this , viewModel.storageData.value.date)

        customDateDialog.setOnClickListener(object : CustomDateDialog.CustomCategoryDialogListener{
            override fun onOkClick(date : String) {
                viewModel.setDate(date)
            }

            override fun onNoClick() {

            }

        })

        customDateDialog.show()
    }

    //수정 완료
    fun onOkClick(){
        viewModel.setData()
        finish()
    }


    //바탕 클릭 (포커스 맞추기)
    fun onBackgroundClick(){
        binding.constraint.isFocusableInTouchMode = true
        binding.constraint.requestFocus()
    }



}