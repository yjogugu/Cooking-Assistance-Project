package com.taijoo.cookingassistance.util

//네트워크 실시간 연결 상태 확인
sealed class NetworkState {
    object None: NetworkState() //최초 상태
    object Connected: NetworkState()//네트워크 연결상태
    object NotConnected: NetworkState()//네트워크 비연결 상태

}