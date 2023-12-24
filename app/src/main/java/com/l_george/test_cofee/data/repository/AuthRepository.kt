package com.l_george.test_cofee.data.repository

import androidx.lifecycle.MutableLiveData
import com.l_george.test_cofee.auth.AppAuth
import com.l_george.test_cofee.api.ApiService
import com.l_george.test_cofee.data.models.UserModel
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val auth: AppAuth
) {
    private var isAuth:Boolean? = auth.token != null
        set(value) {
            field = value
            isAuthLiveData.value = value
        }
    val isAuthLiveData = MutableLiveData(isAuth)

    suspend fun register(userModel: UserModel) {
        val response = apiService.registration(userModel)
        if (response.isSuccessful) {
            val token = response.body()
            auth.saveAuth(token?.token ?: throw Exception())
            isAuth = true
        } else {
            throw Exception()
        }
    }

    suspend fun login(userModel: UserModel) {
        val response = apiService.login(userModel)
        if (response.isSuccessful) {
            val token = response.body()?.token ?: throw Exception()
            auth.saveAuth(token)
            isAuth = true
        } else {
            throw Exception()
        }
    }

    fun logOut(){
        isAuth = null
        auth.clearAuth()
    }

}