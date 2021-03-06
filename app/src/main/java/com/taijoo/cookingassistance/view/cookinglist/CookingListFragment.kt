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
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
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
class CookingListFragment : Fragment() {

    private lateinit var binding : FragmentCookingListBinding
    private val adapter = CookingListAdapter()
    val viewModel : CookingListViewModel by viewModels()


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
                3->{//??????
                }
                else->{
                    adapter.refresh()
                    viewModel.getSelectFoodList((viewModel.jsonObject.toString()))
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
                if(viewModel.type.value == 1){//??????????????? ?????? ???????????? + Room ????????? ??????????????? ???????????? ?????????????????? ??????
                    adapter.refresh()
                    viewModel.getSelectFoodList((viewModel.jsonObject.toString()))
                }
            }
        }


        //????????? ????????? ??????
        adapter.setOnItemClickListener(object : CookingListAdapter.OnItemClickListener{
            override fun onItemClick(view: View, data: CookingListData) {

                val intent = Intent(context , CookingRecipeActivity::class.java)
                intent.putExtra("name" , data.name)
                intent.putExtra("seq" , data.seq)
                intent.putExtra("img" , data.img)
                val options : ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity ,view,"cook")
                startActivity(intent, options.toBundle())

            }
        })

        getData()

        return binding.root
    }

    companion object {
        fun newInstance() = CookingListFragment()
    }

    //?????????????????? ????????? ????????????
    private fun getData(){
        lifecycleScope.launch {
            viewModel.networkState.collectLatest { network->//??????????????? ????????????????????? ??????
                if(network == NetworkState.Connected){//??????????????? ????????????????????? ????????? ????????????
                    launch {
                        viewModel.item.collectLatest {
                            adapter.submitData(it)
                        }
                    }
                    launch {
                        adapter.loadStateFlow.collectLatest {
                            binding.rv.smoothScrollToPosition(0)

                        }
                    }
                }
            }
        }
    }

}