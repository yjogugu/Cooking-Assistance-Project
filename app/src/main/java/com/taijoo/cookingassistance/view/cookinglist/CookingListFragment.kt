package com.taijoo.cookingassistance.view.cookinglist

import android.os.Bundle
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CookingListFragment : Fragment() {

    private lateinit var binding : FragmentCookingListBinding
    private val adapter = CookingListAdapter()
    private val viewModel : CookingListViewModel by viewModels()

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

        getData()


        return binding.root
    }

    companion object {
        fun newInstance() = CookingListFragment()
    }


    private fun getData(){
        lifecycleScope.launch {
            viewModel.getTestData().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}