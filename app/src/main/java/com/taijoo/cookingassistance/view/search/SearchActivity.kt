package com.taijoo.cookingassistance.view.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.databinding.ActivitySearchBinding
import com.taijoo.cookingassistance.util.CustomAutoCompleteAdapter
import com.taijoo.cookingassistance.util.CustomCategoryDialog
import com.taijoo.cookingassistance.util.CustomDefaultDialog
import com.taijoo.cookingassistance.util.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class SearchActivity : AppCompatActivity() , SearchInterface {

    lateinit var binding : ActivitySearchBinding

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var adapter : SearchAdapter
    private var suggestAdapter : CustomAutoCompleteAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_search)


        binding.apply {
            searchViewModel = viewModel
            activity = this@SearchActivity
            searchList.setHasFixedSize(true)
            titleAppbar.ivBack.visibility = View.VISIBLE
            lifecycleOwner = this@SearchActivity
        }

        suggestAdapter = CustomAutoCompleteAdapter(this, viewModel.item.value)
        binding.autoTextView.setAdapter(suggestAdapter)
        binding.autoTextView.isFocusableInTouchMode = true
        binding.autoTextView.requestFocus()

        //?????????????????? ????????? ????????????
        viewModel.listItem.observe(this@SearchActivity){
            if(it.isNotEmpty()){
                adapter.setData(it)
            }

        }

        //???????????? ????????? ????????????
        viewModel.search.observe(this@SearchActivity){
            if(it != ""){
                viewModel.getMaterialList(it)
            }

        }

        init()

    }

    private fun init(){

        //???????????? ????????????
        viewModel.getSearchCategory()

        binding.searchList.layoutManager = LinearLayoutManager(this)
        adapter = SearchAdapter(this)
        binding.searchList.adapter = adapter


        //????????? ????????????
        binding.autoTextView.setOnItemClickListener { adapterView, view, i, l ->
            lifecycleScope.launch {
                viewModel.getStorage(binding.autoTextView.text.toString()).collectLatest {
                    viewModel.setAdapterData(binding.autoTextView.text.toString() , it)
                }
            }
        }


        //????????? ?????? ????????????
        binding.autoTextView.setOnEditorActionListener { textView, actionId, keyEvent ->
            if(actionId == EditorInfo.IME_ACTION_DONE){

                if(viewModel.item.value.isEmpty() && adapter.itemCount == 0){
                    setDefaultDialog()
                }
                else{
                    binding.autoTextView.dismissDropDown()
                    lifecycleScope.launch {
                        viewModel.getStorage(binding.autoTextView.text.toString()).collectLatest {
                            viewModel.setAdapterData(binding.autoTextView.text.toString() , it)
                        }
                    }
                }

                return@setOnEditorActionListener true

            }
            return@setOnEditorActionListener false
        }



        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.item.collect {
                    if(suggestAdapter == null){
                        suggestAdapter = CustomAutoCompleteAdapter(this@SearchActivity, it)
                        binding.autoTextView.setAdapter(suggestAdapter)
                    }
                    else{
                        suggestAdapter!!.addAll(it)
                        binding.autoTextView.setAdapter(suggestAdapter)
                    }
                }

            }

            //???????????? ?????????
            viewModel.networkState.collectLatest {
                if(it == NetworkState.NotConnected){
                    Snackbar
                        .make(binding.constraint, getString(R.string.network_check), 5000)
                        .setBackgroundTint(getColor(R.color.color_Dark333333))
                        .setTextColor(getColor(R.color.color_DarkFFFFFF))
                        .setActionTextColor(getColor(R.color.color_DarkFFFFFF))
                        .setAction("??????"){}.show()
                }
            }

        }


    }

    //????????? ?????? ?????? ?????????
    override fun onItemClick(item : StorageMaterialData) {
        viewModel.setStorage(item)
    }

    //????????? ????????? ???????????? ????????? ??????????????? ?????? ????????????
    private fun setDefaultDialog(){
        val customDefaultDialog = CustomDefaultDialog(this , getString(R.string.dialog_title1) , getString(R.string.dialog_content1))

        customDefaultDialog.setDialogListener(object : CustomDefaultDialog.CustomDialogListener{
            override fun onCheckClick() {

                if (viewModel.networkCheck){
                    setCategorySelect()
                }
                else{
                    Snackbar
                        .make(binding.constraint, getString(R.string.network_check), 5000)
                        .setBackgroundTint(getColor(R.color.color_Dark333333))
                        .setTextColor(getColor(R.color.color_DarkFFFFFF))
                        .setActionTextColor(getColor(R.color.color_DarkFFFFFF))
                        .setAction("??????"){}.show()
                }


            }

            override fun onNoClick() {

            }

        })

        customDefaultDialog.show()
    }

    //???????????? ????????? ?????? db??? ??????
    private fun setCategorySelect(){
        val customCategoryDialog = CustomCategoryDialog(this@SearchActivity,viewModel.categoryItem)
        customCategoryDialog.setOnCategoryListener(object : CustomCategoryDialog.CustomCategoryDialogListener{
            override fun onOkClick(type: Int) {
                if(type != -1){
                    viewModel.setMaterialList(viewModel.categoryItem[type].type,binding.autoTextView.text.toString())
                }
            }

            override fun onNoClick() {

            }

        })
        customCategoryDialog.show()
    }
}