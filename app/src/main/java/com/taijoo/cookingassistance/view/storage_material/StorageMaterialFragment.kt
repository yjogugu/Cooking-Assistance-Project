package com.taijoo.cookingassistance.view.storage_material

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.databinding.FragmentStorageMaterialBinding
import com.taijoo.cookingassistance.util.CustomCategoryDialog
import com.taijoo.cookingassistance.util.CustomDateDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class StorageMaterialFragment : Fragment(),StorageMaterialInterface {

    private lateinit var binding : FragmentStorageMaterialBinding

    private val adapter : StorageMaterialAdapter = StorageMaterialAdapter(this)

    private val viewModel : StorageMaterialViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_storage_material , container , false)

        binding.apply {
            lifecycleOwner = this@StorageMaterialFragment
        }

        binding.storageList.setHasFixedSize(true)
        binding.storageList.layoutManager = LinearLayoutManager(context)
        binding.storageList.adapter = adapter

        getData()

        getObserve()

        return binding.root
    }

    // 재료에 변경사항이 있는지 확인
    private fun getObserve(){
        lifecycleScope.launch {
            viewModel.storage.collect {
                if(it.isNotEmpty()){
                    adapter.refresh()
                }
            }
        }
    }

    //페이징 데이터
    private fun getData(){
        lifecycleScope.launch {
            viewModel.pagingData.collectLatest {
                adapter.submitData(it)
            }
        }

    }

    companion object {
        fun newInstance() = StorageMaterialFragment()
    }

    //리사이클러뷰 아이템 클릭
    override fun itemClick(item : StorageMaterialData) {
        val customDateDialog = CustomDateDialog(requireActivity())

        customDateDialog.setOnClickListener(object : CustomDateDialog.CustomCategoryDialogListener{
            override fun onOkClick(date : String) {
                item.expiration_date = date
                viewModel.setDate(item)
            }

            override fun onNoClick() {

            }

        })

        customDateDialog.show()

    }

}