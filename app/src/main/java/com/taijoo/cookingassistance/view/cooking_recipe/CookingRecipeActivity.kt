package com.taijoo.cookingassistance.view.cooking_recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.databinding.ActivityCookingRecipeBinding

class CookingRecipeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCookingRecipeBinding

    private val viewModel : CookingRecipeViewModel by viewModels()

    private val adapter = CookingRecipeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this,R.layout.activity_cooking_recipe)

        binding.apply {
            viewModel.name = intent.getStringExtra("name").toString()
            viewModel.seq = intent.getIntExtra("seq",0)
            viewModel.img = intent.getStringExtra("img").toString()
            viewmodel  = viewModel
            lifecycleOwner = this@CookingRecipeActivity
        }

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.adapter = adapter
        viewModel.item.observe(this){
            adapter.setData(it as ArrayList<String>)
        }
    }
}