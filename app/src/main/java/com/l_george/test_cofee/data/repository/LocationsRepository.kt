package com.l_george.test_cofee.data.repository

import com.l_george.test_cofee.api.ApiService
import com.l_george.test_cofee.data.models.CoffeeShopModel
import javax.inject.Inject

class LocationsRepository @Inject constructor(private val api: ApiService) {

    suspend fun getLocations(): List<CoffeeShopModel> {

        val response = api.getLocations()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception()
        } else {
            throw Exception()
        }

    }
}