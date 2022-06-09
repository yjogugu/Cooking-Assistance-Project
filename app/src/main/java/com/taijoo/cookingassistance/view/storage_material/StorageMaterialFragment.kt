package com.taijoo.cookingassistance.view.storage_material

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import androidx.recyclerview.widget.SimpleItemAnimator
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.databinding.FragmentStorageMaterialBinding
import com.taijoo.cookingassistance.util.CustomDateDialog
import com.taijoo.cookingassistance.view.storage_material_setting.StorageMaterialSettingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.round


@AndroidEntryPoint
class StorageMaterialFragment : Fragment(),StorageMaterialInterface {

    private lateinit var binding : FragmentStorageMaterialBinding

    private val adapter : StorageMaterialAdapter = StorageMaterialAdapter(this)

    private val viewModel : StorageMaterialViewModel by viewModels()

    private lateinit var animator: ItemAnimator
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_storage_material , container , false)

        binding.apply {
            lifecycleOwner = this@StorageMaterialFragment
        }
        binding.storageList.setHasFixedSize(true)
        binding.storageList.layoutManager = LinearLayoutManager(context)

        animator = binding.storageList.itemAnimator!!
        if (animator is SimpleItemAnimator) {
            (animator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        binding.storageList.adapter = adapter

        getData()

        return binding.root
    }


    //페이징 데이터
    private fun getData(){
        lifecycleScope.launch {
            launch {
                viewModel.pagingData.collect{
                    adapter.submitData(lifecycle,it)
                }
            }
            launch {//재료에 변화가 있는지 체크
                viewModel.storage.collectLatest {
                    adapter.setData(it,viewModel.adapterPosition)
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        if(viewModel.viewType == 1){
            adapter.refresh()
            viewModel.viewType = 0
        }
    }

    companion object {
        fun newInstance() = StorageMaterialFragment()
    }

    //리사이클러뷰 아이템 클릭
    override fun itemClick(item : StorageMaterialData , position : Int) {
        viewModel.setStorage(item,position)
        val intent = Intent(requireContext(), StorageMaterialSettingActivity::class.java)
        intent.putExtra("seq",item.seq)
        startActivity(intent)
    }

    fun setViewType(viewType : Int){
        viewModel.viewType = viewType
    }

}