package com.taijoo.cookingassistance.view.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor() : ViewModel() {


    private val _item = MutableStateFlow("")
    val item : StateFlow<String> get() = _item.asStateFlow()

//    val liveData = MutableLiveData<String>()
    val liveData = MutableStateFlow<String>("")

    fun setItem(string: String){
        viewModelScope.launch {
            _item.emit(string)
        }

    }
}