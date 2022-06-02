package com.taijoo.cookingassistance.view.cookinglist

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.data.model.CookingListData
import com.taijoo.cookingassistance.databinding.FragmentCookingListBinding
import com.taijoo.cookingassistance.util.NetworkState
import com.taijoo.cookingassistance.view.MainActivity
import com.taijoo.cookingassistance.view.MainInterface
import com.taijoo.cookingassistance.view.cooking_recipe.CookingRecipeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class CookingListFragment () : Fragment() {

    private lateinit var binding : FragmentCookingListBinding
    private val adapter = CookingListAdapter()
    val viewModel : CookingListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cooking_list , container , false)


        binding.apply {
            lifecycleOwner = this@CookingListFragment
        }

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.adapter = adapter

        viewModel.type.observe(viewLifecycleOwner){type->
            when(type){
                3->{//검색
                }
                else->{
                    adapter.refresh()
                    getData()
                }
            }

        }

        lifecycleScope.launch {
            viewModel.storage.collectLatest {
                viewModel.jsonArray = JSONArray()
                for (i in it){
                    viewModel.jsonArray.put(i)
                }
                viewModel.jsonObject.put("data",viewModel.jsonArray)
                if(viewModel.type.value == 1){//요리가능한 목록 상태일때 + Room 자기가 가지고있는 데이터가 변경되었을때 변경
                    adapter.refresh()
                    getData()
                }
            }
        }


        //어뎁터 아이템 클릭
        adapter.setOnItemClickListener(object : CookingListAdapter.OnItemClickListener{
            override fun onItemClick(view: View, data: CookingListData) {

                val intent = Intent(context , CookingRecipeActivity::class.java)
                intent.putExtra("name" , data.name)
                intent.putExtra("seq" , data.seq)
                intent.putExtra("img" , data.img)
                val options : ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity ,view,"test")
                startActivity(intent, options.toBundle())

            }
        })

        return binding.root
    }


    companion object {
        fun newInstance() = CookingListFragment()
    }

    //리사이클러뷰 아이템 뿌려주기

    private fun getData(){

        lifecycleScope.launch {
            viewModel.networkState.collectLatest { network->//네트워크가 연결되어있는지 확인
                if(network == NetworkState.Connected){//네트워크가 연결되어있으면 데이터 불러오기
                    viewModel.getSelectFoodList(viewModel.jsonObject.toString()).collectLatest {
                        adapter.submitData(it)
                        adapter.loadStateFlow.collectLatest {
                            binding.rv.smoothScrollToPosition(0)

                        }
                    }
                }
            }
        }
    }

}