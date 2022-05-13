package com.taijoo.cookingassistance.data.model

import com.google.gson.annotations.SerializedName

data class SearchMaterialData(
    @SerializedName("response") val response : MaterialResponse
)

data class MaterialResponse(
    @SerializedName("result") val result : Boolean,
    @SerializedName("data") val data : List<MaterialData>
)


data class MaterialData(
    @SerializedName("seq") val seq : Int,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : Int,
    @SerializedName("date") val date : String
)