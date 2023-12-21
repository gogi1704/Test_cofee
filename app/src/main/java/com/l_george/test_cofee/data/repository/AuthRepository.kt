package com.l_george.test_cofee.data.repository

import com.l_george.test_cofee.AppAuth
import com.l_george.test_cofee.api.ApiService
import com.l_george.test_cofee.data.models.ResponseAuthModel
import com.l_george.test_cofee.data.models.UserModel
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val auth: AppAuth
) {

    suspend fun register(userModel: UserModel) {
        val response = apiService.registration(userModel)
        if (response.isSuccessful) {
            val token = response.body()
            auth.saveAuth(token?.token ?: throw Exception())
            token.token
        } else {
            throw Exception()
        }
    }

    suspend fun login(userModel: UserModel) {
        val response = apiService.login(userModel)
        if (response.isSuccessful) {
           val token = response.body()?.token ?: throw Exception()
            token
        } else {
            throw Exception()
        }
    }

}