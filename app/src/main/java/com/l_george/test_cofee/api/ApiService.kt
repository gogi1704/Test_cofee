package com.l_george.test_cofee.api

import com.l_george.test_cofee.data.models.ResponseAuthModel
import retrofit2.Response
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    suspend fun registration(login: String, password: String): Response<ResponseAuthModel>

    @POST("auth/login")
    suspend fun login(login: String, password: String): Response<ResponseAuthModel>
}