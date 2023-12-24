package com.l_george.test_cofee.data.repository

import com.l_george.test_cofee.api.ApiService
import com.l_george.test_cofee.data.models.MenuModel
import javax.inject.Inject

class MenuRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getMenuByLocationId(id:Int):List<MenuModel>{
        try {
            val response = apiService.getMenuByLocationById(id)
            if (response.isSuccessful){
                return response.body() ?: throw Exception()
            }else{
                throw Exception()
            }
        }catch (e:Exception){
            throw Exception()
        }
    }
}