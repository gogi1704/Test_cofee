package com.l_george.test_cofee.ui.viewModels.authViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l_george.test_cofee.data.models.UserModel
import com.l_george.test_cofee.data.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    val isAuthLiveData: MutableLiveData<Boolean?>
        get() = repository.isAuthLiveData


    fun register(userModel: UserModel) {
        viewModelScope.launch {
            repository.register(userModel)
        }
    }

    fun logIn(userModel: UserModel) {
        viewModelScope.launch {
            repository.login(userModel)

        }

    }

    fun logOut(){
        repository.logOut()
    }

}