package com.taijoo.cookingassistance.view.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.databinding.ActivityTestBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestActivity : AppCompatActivity() {

    private lateinit var binding :ActivityTestBinding
    private val viewModel : TestViewModel by viewModels()


    var a = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_test)


        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@TestActivity
        }


        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.item.collectLatest {
                    Log.e("여기","11ㅇㅇ"+it)
                }

                viewModel.item.collect {
                    Log.e("여기","22ㅇㅇ"+it)
                }


            }

            viewModel.liveData.collectLatest {
                Log.e("여기","33ㅇㅇ"+it)
            }

            viewModel.liveData.collect {
                Log.e("여기","44ㅇㅇ"+it)
            }
        }
//        viewModel.liveData.observe(this@TestActivity){
//            Log.e("여기" ,"44ㅇㅇ"+it)
//        }


        binding.button.setOnClickListener {
            a++
            lifecycleScope.launch {
                viewModel.setItem(viewModel.liveData.value.toString())
            }

        }


    }
}