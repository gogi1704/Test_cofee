package com.l_george.test_cofee.data.repository

import com.l_george.test_cofee.api.ApiService
import com.l_george.test_cofee.data.models.BagModel
import com.l_george.test_cofee.data.models.MenuModel
import javax.inject.Inject

class MenuRepository @Inject constructor(private val apiService: ApiService) {

    var bag = mutableListOf<BagModel>()
        set(value) {
            field = value
            field
        }
    private var lastList = listOf<MenuModel>()

    suspend fun getMenuByLocationId(id: Int): List<MenuModel> {
        try {
            val response = apiService.getMenuByLocationById(id)
            if (response.isSuccessful) {
                lastList = response.body() ?: emptyList()
                return response.body() ?: throw Exception()
            } else {
                throw Exception()
            }
        } catch (e: Exception) {
            throw Exception()
        }
    }

    fun addToBag(menuModel: MenuModel) {
        if (bag.none { it.menuModel.id == menuModel.id }) {
            bag.add(BagModel(menuModel, 1))
        } else {
            bag =
                bag.map { if (it.menuModel.id == menuModel.id) it.copy(countItems = it.countItems+1) else it }
                    .toMutableList()
        }
    }

    fun removeFromBag(menuModel: MenuModel) {
        if (bag.any { it.menuModel.id == menuModel.id && it.countItems > 0 }) {
            bag =
                bag.map { if (it.menuModel.id == menuModel.id) it.copy(countItems = it.countItems-1) else it }
                    .toMutableList()
        }
    }

    fun getResult(): List<Pair<MenuModel, Int>> {
        val listResult = mutableListOf<Pair<MenuModel, Int>>()

        return if (lastList.isNotEmpty()) {
            for (bagModel in bag) {
                val menuPair: Pair<MenuModel, Int> = Pair(bagModel.menuModel, bagModel.countItems)
                listResult.add(menuPair)
            }
            listResult.filter { it.second > 0 }
        } else {
            emptyList()
        }

    }

}