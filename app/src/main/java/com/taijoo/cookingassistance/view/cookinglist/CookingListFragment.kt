package com.taijoo.cookingassistance.view.cookinglist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.databinding.FragmentCookingListBinding
import com.taijoo.cookingassistance.view.MainActivity
import com.taijoo.cookingassistance.view.MainInterface
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

@AndroidEntryPoint
class CookingListFragment : Fragment() {

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
                0->{//전체
                    getData()
                }
                1->{
                    getData()
                }
                3->{

                }
            }

        }

        viewModel.storage.observe(viewLifecycleOwner){

//            for (i in it){
//                jsonArray.put(i)
//            }
            viewModel.jsonArray.put("김치")
            viewModel.jsonArray.put("두부")
            viewModel.jsonArray.put("돼지고기앞다리")
            viewModel.jsonArray.put("소고기")
            viewModel.jsonObject.put("data",viewModel.jsonArray)

            getData()
        }

        return binding.root
    }

    companion object {
        fun newInstance() = CookingListFragment()
    }


    //리사이클러뷰 아이템 뿌려주기

    private fun getData(){
        lifecycleScope.launch {
            viewModel.getSelectFoodList(viewModel.jsonObject.toString()).collectLatest {
                adapter.submitData(it)
            }
        }
    }

}