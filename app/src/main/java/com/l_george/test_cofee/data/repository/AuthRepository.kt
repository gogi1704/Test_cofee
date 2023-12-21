package com.l_george.test_cofee.data.repository

import com.l_george.test_cofee.api.ApiService
import com.l_george.test_cofee.data.models.ResponseAuthModel
import com.l_george.test_cofee.data.models.UserModel
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun register(userModel: UserModel): ResponseAuthModel {
        val response = apiService.registration(userModel)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception()
        } else {
            throw Exception()
        }
    }

    suspend fun login(userModel: UserModel): ResponseAuthModel {
        val response = apiService.login(userModel)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception()
        } else {
            throw Exception()
        }
    }

}