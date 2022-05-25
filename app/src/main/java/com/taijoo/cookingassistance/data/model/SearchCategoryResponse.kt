package com.taijoo.cookingassistance.data.model

import com.google.gson.annotations.SerializedName

data class SearchCategoryResponse (@SerializedName("request") val request : Boolean ,
                                   @SerializedName("data") val data : SearchCategoryResult)


data class SearchCategoryResult (@SerializedName("request") val request : Boolean ,
                                 @SerializedName("data") val data : List<SearchCategoryData>)


data class SearchCategoryData (@SerializedName("seq") val seq : Int ,
                               @SerializedName("category") val category : String ,
                               @SerializedName("type") val type : Int)