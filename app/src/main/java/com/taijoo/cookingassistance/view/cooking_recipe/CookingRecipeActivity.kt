package com.taijoo.cookingassistance.view.cooking_recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.databinding.ActivityCookingRecipeBinding
import com.taijoo.cookingassistance.util.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CookingRecipeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCookingRecipeBinding

    private val viewModel : CookingRecipeViewModel by viewModels()

    private val adapter : CookingRecipeAdapter = CookingRecipeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this,R.layout.activity_cooking_recipe)

        binding.apply {
            viewmodel  = viewModel
            activity = this@CookingRecipeActivity
            viewModel.name = intent.getStringExtra("name").toString()
            viewModel.seq = intent.getIntExtra("seq",0)
            viewModel.img = intent.getStringExtra("img").toString()
            lifecycleOwner = this@CookingRecipeActivity
        }

        init()
        getNetworkCheck()
        getRecipe()

    }

    private fun init(){
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.getRecipe()//레시피 가져오기
    }

    //네트워크 체크용
    private fun getNetworkCheck(){
        lifecycleScope.launch {

            viewModel.networkState.collectLatest {
                if(it == NetworkState.NotConnected){
                    Snackbar
                        .make(binding.coordinator, getString(R.string.network_check), 5000)
                        .setBackgroundTint(getColor(R.color.color_Dark333333))
                        .setTextColor(getColor(R.color.color_DarkFFFFFF))
                        .setActionTextColor(getColor(R.color.color_DarkFFFFFF))
                        .setAction("확인"){}.show()
                }
            }


        }
    }

    //데이터 UI 반영
    private fun getRecipe(){
        lifecycleScope.launch {
            viewModel.item.collect {
                adapter.setData(it)
            }
        }
    }
}