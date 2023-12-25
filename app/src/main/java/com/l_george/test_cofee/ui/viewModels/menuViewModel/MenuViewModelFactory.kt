package com.l_george.test_cofee.ui.viewModels.menuViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.l_george.test_cofee.data.repository.AuthRepository
import com.l_george.test_cofee.data.repository.MenuRepository

class MenuViewModelFactory(private val menuRepository: MenuRepository
 , private val authRepository: AuthRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

       return MenuViewModel(menuRepository , authRepository) as T
    }
}