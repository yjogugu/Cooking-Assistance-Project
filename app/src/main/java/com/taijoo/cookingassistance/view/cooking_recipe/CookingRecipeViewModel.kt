package com.taijoo.cookingassistance.view.cooking_recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taijoo.cookingassistance.data.model.CookingRecipeData
import com.taijoo.cookingassistance.data.model.CookingRecipeResponse
import com.taijoo.cookingassistance.data.repository.room.repository.CookingRepository
import com.taijoo.cookingassistance.util.NetworkChecker
import com.taijoo.cookingassistance.util.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okio.IOException
import org.json.JSONArray
import javax.inject.Inject

@HiltViewModel
class CookingRecipeViewModel @Inject constructor(private val repository : CookingRepository , private val networkChecker: NetworkChecker): ViewModel() {

    lateinit var name : String
    var seq : Int = 0
    lateinit var img : String


    private var _item = MutableStateFlow(emptyList<CookingRecipeData>())//요리 내용
    val item : StateFlow<List<CookingRecipeData>> get() = _item

    private val _networkState = MutableSharedFlow<NetworkState>(replay = 1)
    val networkState: SharedFlow<NetworkState> get() = _networkState//네트워크 체크용


    init {
        getNetwork()

    }

    //네트워크 체크
    private fun getNetwork(){
        viewModelScope.launch {

            networkChecker.networkState.collectLatest {
                _networkState.emit(it)
            }

        }
    }

    //서버에서 레시피 데이터 가져오기
    fun getRecipe(){
        viewModelScope.launch {
            try {
                val response = repository.getRecipe(seq)

                when(response.isSuccessful){
                    true->{
                        val body = response.body()!!.data
                        val item = ArrayList<CookingRecipeData>()

                        item.add(CookingRecipeData(1,body.main_material,body.sub_material,body.comment))

                        _item.emit(item)

                    }
                    false->{

                    }
                }


            }
            catch (e : IOException){
            }
            catch (e : Exception){

            }

        }

    }
}