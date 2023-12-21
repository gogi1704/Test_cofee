package com.l_george.test_cofee.data.repository

import com.l_george.test_cofee.api.ApiService
import com.l_george.test_cofee.data.models.ResponseAuthModel
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun register(login: String, password: String): ResponseAuthModel {
        val response = apiService.registration(login, password)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception()
        } else {
            throw Exception()
        }
    }

    suspend fun login(login: String, password: String): ResponseAuthModel {
        val response = apiService.login(login, password)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception()
        } else {
            throw Exception()
        }
    }

}