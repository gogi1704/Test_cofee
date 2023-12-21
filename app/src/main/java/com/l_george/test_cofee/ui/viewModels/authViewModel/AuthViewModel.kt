package com.l_george.test_cofee.ui.viewModels.authViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l_george.test_cofee.data.models.UserModel
import com.l_george.test_cofee.data.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    fun register(){
        viewModelScope.launch {
            val auth = repository.register(UserModel("vasua" , "12345"))
            auth
        }
    }

    fun logIn(){
        viewModelScope.launch {
            val auth = repository.login(UserModel("vasua" , "12345"))
            auth
        }

    }
    init {
      logIn()
    }
}