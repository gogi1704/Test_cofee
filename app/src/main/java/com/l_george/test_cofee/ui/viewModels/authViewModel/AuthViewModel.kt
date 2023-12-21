package com.l_george.test_cofee.ui.viewModels.authViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l_george.test_cofee.data.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    fun register(){
        viewModelScope.launch {
            val auth = repository.register("" , "")
            auth
        }
    }
    init {
        register()
    }
}