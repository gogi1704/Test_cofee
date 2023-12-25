package com.l_george.test_cofee.ui.viewModels.menuViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l_george.test_cofee.data.models.MenuModel
import com.l_george.test_cofee.data.repository.MenuRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuViewModel @Inject constructor(private val menuRepository: MenuRepository) : ViewModel() {

    private var menuList = emptyList<MenuModel>()
        set(value) {
            field = value
            menuListLiveData.value = value
        }
    val menuListLiveData = MutableLiveData(menuList)

    fun getMenuById(id: Int) {
        viewModelScope.launch {
            menuList = menuRepository.getMenuByLocationId(id)
        }

    }

    fun addToBag(menuModel: MenuModel) {
        menuRepository.addToBag(menuModel)
    }

    fun removeFromBag(menuModel: MenuModel) {
        menuRepository.removeFromBag(menuModel)
    }

    fun getResult(): List<Pair<MenuModel, Int>> {
        val i = menuRepository.getResult()
        i
        return menuRepository.getResult()
    }

}