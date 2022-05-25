package com.taijoo.cookingassistance.data.model

import com.google.gson.annotations.SerializedName

data class CookingRecipeResponse (@field:SerializedName("request") val request : Boolean  , @field:SerializedName("data") val cookingData : CookingRecipeData)


data class CookingRecipeData(val viewType : Int ,
                       @field:SerializedName("seq") val seq : Int ,
                       @field:SerializedName("seq") val name : String ,
                       @field:SerializedName("seq") val img : String ,
                       @field:SerializedName("seq") val comment : String)