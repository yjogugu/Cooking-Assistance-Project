package com.taijoo.cookingassistance.view.search

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.flow.collect
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
            searchList.setHasFixedSize(true)
            lifecycleOwner = this@SearchActivity
        }

        suggestAdapter = CustomAutoCompleteAdapter(this, viewModel.item.value!!)
        binding.autoTextView.setAdapter(suggestAdapter)
        binding.autoTextView.isFocusableInTouchMode = true
        binding.autoTextView.requestFocus()


        //검색 리스트 보여주기
        viewModel.item.observe(this){
            if(suggestAdapter == null){
                suggestAdapter = CustomAutoCompleteAdapter(this, it)
                binding.autoTextView.setAdapter(suggestAdapter)
            }
            else{
                suggestAdapter!!.addAll(it)
                binding.autoTextView.setAdapter(suggestAdapter)
            }


        }


        //서버에서 데이터 가져오기
        viewModel.search.observe(this){
            if(!it.equals("")){
                viewModel.getMaterialList(it)
            }

        }

        //리사이클러뷰 아이템 뿌려주기
        viewModel.listItem.observe(this){
            if(it.isNotEmpty()){
                adapter.setData(it)
            }

        }



        init()

    }

    private fun init(){

        //카테고리 가져오기
        viewModel.getSearchCategory()

        binding.searchList.layoutManager = LinearLayoutManager(this)
        adapter = SearchAdapter(this)
        binding.searchList.adapter = adapter


        binding.autoTextView.setOnItemClickListener { adapterView, view, i, l ->

            viewModel.getStorage(binding.autoTextView.text.toString()).observe(this){
                viewModel.setAdapterData(binding.autoTextView.text.toString() , it)
            }

        }


        binding.autoTextView.setOnEditorActionListener { textView, actionId, keyEvent ->
            if(actionId == EditorInfo.IME_ACTION_DONE){

                viewModel.getStorage(binding.autoTextView.text.toString()).observe(this){
                    if(it.isEmpty()){//데이터가 없으면 데이터를 추가할지 묻는다
                        setDefaultDialog()
                    }
                    else{
                        viewModel.setAdapterData(binding.autoTextView.text.toString() , it)
                    }

                }


                return@setOnEditorActionListener true

            }
            return@setOnEditorActionListener false
        }


        lifecycleScope.launch {

            viewModel.networkState.collect {
                if(it == NetworkState.NotConnected){
                    Snackbar
                        .make(binding.constraint, getString(R.string.network_check), 5000)
                        .setBackgroundTint(getColor(R.color.color_Dark333333))
                        .setTextColor(getColor(R.color.color_DarkFFFFFF))
                        .setActionTextColor(getColor(R.color.color_DarkFFFFFF))
                        .setAction("확인"){}.show()
                }
            }
        }

    }

    //검색한 내용 갯수 카운트
    override fun onItemClick(item : StorageMaterialData) {
        viewModel.setStorage(item)
    }

    //서버에 재료가 없는경우 서버에 추가할지의 여부 물어보기
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
                        .setAction("확인"){}.show()
                }


            }

            override fun onNoClick() {

            }

        })

        customDefaultDialog.show()
    }

    //카테고리 선택후 서버 db에 저장
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