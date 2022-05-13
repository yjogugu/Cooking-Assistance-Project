package com.taijoo.cookingassistance.data.repository.http

import com.taijoo.cookingassistance.data.model.SearchMaterialData
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ServerApi {

    companion object {
        private const val BASE_URL = "https://yjo0909.cafe24.com/RestfulApi/"

        fun create(): ServerApi {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ServerApi::class.java)
        }
    }


    @GET("SelectMaterialList.php")
    suspend fun getSearchMaterialData(@Query("search") search : String): Response<SearchMaterialData>


    @FormUrlEncoded
    @POST("InsertMaterialList.php")
    suspend fun setSearchMaterialData(@Field("search") search : String): Response<SearchMaterialData>


    @GET("test.php")
    suspend fun test(@Query("page") page: Int,
                     @Query("per_page") perPage: Int,): Response<ResponseBody>

}