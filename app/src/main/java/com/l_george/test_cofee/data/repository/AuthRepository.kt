package com.l_george.test_cofee.data.repository

import androidx.lifecycle.MutableLiveData
import com.l_george.test_cofee.auth.AppAuth
import com.l_george.test_cofee.api.ApiService
import com.l_george.test_cofee.data.models.UserModel
import com.l_george.test_cofee.utils.ApiError
import com.l_george.test_cofee.utils.NetworkError
import com.l_george.test_cofee.utils.UnknownError
import java.io.IOException
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val auth: AppAuth
) {
    private var isAuth: Boolean? = auth.token != null
        set(value) {
            field = value
            isAuthLiveData.value = value
        }
    val isAuthLiveData = MutableLiveData(isAuth)

    suspend fun register(userModel: UserModel) {
        try {
            val response = apiService.registration(userModel)
            if (response.isSuccessful) {
                val token = response.body()
                auth.saveAuth(token?.token ?: throw ApiError())
                isAuth = true
            } else {
                throw ApiError()
            }
        } catch (io: IOException) {
            throw NetworkError()
        } catch (api: ApiError) {
            throw ApiError()
        } catch (e: Exception) {
            throw UnknownError()
        }


    }

    suspend fun login(userModel: UserModel) {

        try {
            val response = apiService.login(userModel)
            if (response.isSuccessful) {
                val token = response.body()?.token ?: throw ApiError()
                auth.saveAuth(token)
                isAuth = true
            } else {
                throw ApiError()
            }
        } catch (io: IOException) {
            throw NetworkError()
        } catch (api: ApiError) {
            throw ApiError()
        } catch (e: Exception) {
            throw UnknownError()
        }

    }

    fun logOut() {
        isAuth = null
        auth.clearAuth()
    }

}