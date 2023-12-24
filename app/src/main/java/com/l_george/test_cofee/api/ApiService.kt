package com.l_george.test_cofee.api

import com.l_george.test_cofee.data.models.CoffeeShopModel
import com.l_george.test_cofee.data.models.MenuModel
import com.l_george.test_cofee.data.models.ResponseAuthModel
import com.l_george.test_cofee.data.models.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/register")
    suspend fun registration(
       @Body user:UserModel
    ): Response<ResponseAuthModel>

    @POST("auth/login")
    suspend fun login(
        @Body user:UserModel
    ): Response<ResponseAuthModel>

    @GET("locations")
    suspend fun getLocations():Response<List<CoffeeShopModel>>

    @GET("location/{id}/menu")
    suspend fun getMenuByLocationById(@Path("id") id:Int):Response<List<MenuModel>>


}