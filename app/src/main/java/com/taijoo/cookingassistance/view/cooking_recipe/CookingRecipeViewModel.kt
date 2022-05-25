package com.taijoo.cookingassistance.view.cooking_recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taijoo.cookingassistance.data.model.CookingRecipeResponse
import com.taijoo.cookingassistance.data.repository.room.repository.CookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CookingRecipeViewModel @Inject constructor(): ViewModel() {

    lateinit var name : String
    var seq : Int = 0
    lateinit var img : String

//    private var _item = MutableLiveData<List<CookingRecipeResponse>>()
//    val item : LiveData<List<String>> get() = _item
//

    private var _item = MutableLiveData<List<String>>()
    val item : LiveData<List<String>> get() = _item

    init {
        val array = ArrayList<String>()
        for(i in 0 until 30){
            array.add(i.toString())
        }
        _item.value = array

    }
}