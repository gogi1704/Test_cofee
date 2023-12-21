package com.l_george.test_cofee.data.repository

import com.l_george.test_cofee.api.ApiService
import javax.inject.Inject

class LocationsRepository @Inject constructor(private val api: ApiService) {

    suspend fun getLocations(){
        val locations = api.getLocations().body()
        locations
    }
}