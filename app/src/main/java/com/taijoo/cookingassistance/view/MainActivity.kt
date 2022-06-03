package com.taijoo.cookingassistance.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.databinding.ActivityMainBinding
import com.taijoo.cookingassistance.util.NetworkState
import com.taijoo.cookingassistance.view.cookinglist.CookingListFragment
import com.taijoo.cookingassistance.view.search.SearchActivity
import com.taijoo.cookingassistance.view.storage_material.StorageMaterialFragment
import com.taijoo.cookingassistance.view.test.TestActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    var fragmentList = ArrayList<Fragment>()

    private val storageMaterialFragment = StorageMaterialFragment.newInstance()//보관중인 요리 재료
    private val cookingListFragment = CookingListFragment.newInstance()//서버에서 받아온 요리레시피 프래그먼트

    private lateinit var popup : PopupMenu

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.apply {
            fragmentList.add(storageMaterialFragment)//보관중인 요리 재료
            fragmentList.add(cookingListFragment)//요리 레시피
            titleAppbar.ivSearch.visibility = View.VISIBLE
            lifecycleOwner = this@MainActivity
        }

        popup = PopupMenu(this,binding.titleAppbar.ivSearch)
        popup.inflate(R.menu.main_menu)


        binding.viewPager.adapter = MainAdapter(supportFragmentManager , lifecycle , fragmentList)


        viewPager()

        binding.titleAppbar.ivSearch.setOnClickListener {
            when(binding.bottomBar.selectedIndex){
                0->{
                    startActivity(Intent(this,SearchActivity::class.java))
                }
                1->{
                    onPopupClick()
                    popup.show()

                }
            }
        }

        //네트워크 연결 체크
        lifecycleScope.launch {
            viewModel.networkState.collectLatest {
                if(NetworkState.NotConnected == it){
                    Snackbar
                        .make(binding.constraint, getString(R.string.network_check), 5000)
                        .setBackgroundTint(getColor(R.color.color_Dark333333))
                        .setTextColor(getColor(R.color.color_DarkFFFFFF))
                        .setActionTextColor(getColor(R.color.color_DarkFFFFFF))
                        .setAction("확인"){}.show()
                }
            }

        }
    }

    //뷰페이저 셋팅
    private fun viewPager(){
        binding.bottomBar.addTab(binding.bottomBar.createTab(R.drawable.icon_shopping , getString(R.string.StorageMaterial)))
        binding.bottomBar.addTab(binding.bottomBar.createTab(R.drawable.icon_list , getString(R.string.CookingList)))
        binding.bottomBar.setupWithViewPager2(binding.viewPager)
    }

    //팝업 메뉴 클릭
    private fun onPopupClick(){
        popup.setOnMenuItemClickListener{ menuItem: MenuItem? ->
            when(menuItem!!.itemId){
                R.id.span_count_1->{//전체목록
                    cookingListFragment.viewModel.type.value = 0
                }
                R.id.span_count_2->{//가능한목록
                    cookingListFragment.viewModel.type.value = 1
                }
                R.id.span_count_3->{//검색
                    cookingListFragment.viewModel.type.value = 2
                }
            }
            true
        }
    }


}

