package com.l_george.test_cofee.data.repository

import com.l_george.test_cofee.api.ApiService
import com.l_george.test_cofee.data.models.CoffeeShopModel
import com.l_george.test_cofee.utils.ApiError
import com.l_george.test_cofee.utils.AuthError
import com.l_george.test_cofee.utils.NetworkError
import com.l_george.test_cofee.utils.UnknownError
import java.io.IOException
import javax.inject.Inject

class LocationsRepository @Inject constructor(private val api: ApiService) {

    suspend fun getLocations(): List<CoffeeShopModel> {
        try {
            val response = api.getLocations()
            if (response.isSuccessful) {
                return response.body() ?: throw ApiError()
            } else if (response.code() == 401) {
                throw AuthError()
            } else {
                throw ApiError()
            }
        } catch (io: IOException) {
            throw NetworkError()
        } catch (api: ApiError) {
            throw ApiError()
        } catch (auth: AuthError) {
            throw AuthError()
        } catch (e: Exception) {
            throw UnknownError()
        }


    }


}