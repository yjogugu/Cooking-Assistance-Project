package com.taijoo.cookingassistance.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.databinding.ActivityMainBinding
import com.taijoo.cookingassistance.view.cookinglist.CookingListFragment
import com.taijoo.cookingassistance.view.search.SearchActivity
import com.taijoo.cookingassistance.view.storage_material.StorageMaterialFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    var fragmentList = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.apply {
            fragmentList.add(StorageMaterialFragment.newInstance())//보관중인 요리 재료
            fragmentList.add(CookingListFragment.newInstance())//요리 레시피

            lifecycleOwner = this@MainActivity
        }

        binding.viewPager.adapter = MainAdapter(supportFragmentManager , lifecycle , fragmentList)


        viewPager()

        binding.titleAppbar.tvSearch.setOnClickListener {
            startActivity(Intent(this,SearchActivity::class.java))
        }
    }

    //뷰페이저 셋팅
    private fun viewPager(){
        binding.bottomBar.addTab(binding.bottomBar.createTab(R.drawable.icon_call , getString(R.string.StorageMaterial)))
        binding.bottomBar.addTab(binding.bottomBar.createTab(R.drawable.icon_call , getString(R.string.CookingList)))
        binding.bottomBar.setupWithViewPager2(binding.viewPager)
    }
}