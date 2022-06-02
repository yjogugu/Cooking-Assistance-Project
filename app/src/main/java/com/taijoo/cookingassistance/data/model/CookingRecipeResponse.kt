package com.taijoo.cookingassistance.data.model

import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class CookingRecipeResponse (@field:SerializedName("request") val request : Boolean  , @field:SerializedName("data") val data : CookingRecipeData)


data class CookingRecipeData(val viewType : Int ,
                             @field:SerializedName("main_material") val main_material : String,
                             @field:SerializedName("sub_material") val sub_material : String,
                             @field:SerializedName("comment") val comment : String){

}

