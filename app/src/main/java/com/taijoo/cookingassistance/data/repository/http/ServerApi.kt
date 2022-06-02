package com.taijoo.cookingassistance.data.repository.http

import com.google.gson.GsonBuilder
import com.taijoo.cookingassistance.data.model.*
import com.taijoo.cookingassistance.util.IP
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ServerApi {

    companion object {
        private const val BASE_URL = IP.SERVER_IP+"RestfulApi/"

        fun create(): ServerApi {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val gson = GsonBuilder() .setLenient() .create()

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ServerApi::class.java)
        }
    }


    //검색 데이터 가져오기
    @GET("SelectMaterialList.php")
    suspend fun getSearchMaterialData(@Query("search") search : String): Response<SearchMaterialData>


    //서버에 없는 재료 넣기
    @FormUrlEncoded
    @POST("InsertMaterialList.php")
    suspend fun setSearchMaterialData(@Field("type") type : Int , @Field("search") search : String): Response<SearchMaterialData>


    //요리가능한 리스트
    @FormUrlEncoded
    @POST("SelectFoodList.php")
    suspend fun getSelectFoodList(@Field("type") type : Int , @Field("seq") seq : Int, @Field("localData") localData : String): Response<CookingListResponse>

    //전체 요리 리스트
    @FormUrlEncoded
    @POST("SelectFoodList.php")
    suspend fun getSelectFoodList(@Field("type") type : Int , @Query("page") page : Int, @Query("per_page") per_page : Int): Response<CookingListResponse>

    //재료 카테고리 가져오기
    @FormUrlEncoded
    @POST("SelectCategory.php")
    suspend fun getSelectCategory(@Field("request") request : String): Response<SearchCategoryResponse>

    //레시피 가져오기
    @GET("SelectRecipe.php")
    suspend fun getSelectRecipe(@Query("request") request : String , @Query("seq") seq : Int): Response<CookingRecipeResponse>

}