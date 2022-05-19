package com.taijoo.cookingassistance.data.model

import com.google.gson.annotations.SerializedName

data class CookingListResponse(@field:SerializedName("request") val request : Boolean ,
                               @field:SerializedName("data") val data : CookingListResult ) {
}

data class CookingListResult(@field:SerializedName("result") val result : Boolean ,
                           @field:SerializedName("data") var data : List<CookingListData>)

data class CookingListData(@field:SerializedName("seq") val seq : Int ,
                           @field:SerializedName("name") var name : String)