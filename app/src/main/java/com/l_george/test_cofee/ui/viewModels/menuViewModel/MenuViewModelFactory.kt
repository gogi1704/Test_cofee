package com.l_george.test_cofee.ui.viewModels.menuViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.l_george.test_cofee.api.ApiService
import com.l_george.test_cofee.data.repository.MenuRepository

class MenuViewModelFactory(private val menuRepository: MenuRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

       return MenuViewModel(menuRepository) as T
    }
}