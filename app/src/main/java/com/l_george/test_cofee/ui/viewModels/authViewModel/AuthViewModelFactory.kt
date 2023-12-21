package com.l_george.test_cofee.ui.viewModels.authViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.l_george.test_cofee.data.repository.AuthRepository

class AuthViewModelFactory(private val authRepository: AuthRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(authRepository) as T
    }
}