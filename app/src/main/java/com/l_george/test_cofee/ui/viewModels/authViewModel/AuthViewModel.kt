package com.l_george.test_cofee.ui.viewModels.authViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l_george.test_cofee.data.models.UserModel
import com.l_george.test_cofee.data.repository.AuthRepository
import com.l_george.test_cofee.utils.ApiError
import com.l_george.test_cofee.utils.AppError
import com.l_george.test_cofee.utils.AuthError
import com.l_george.test_cofee.utils.NetworkError
import com.l_george.test_cofee.utils.UnknownError
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private var errorState: AppError? = null
        set(value) {
            field = value
            errorLiveData.value = value
        }

    val errorLiveData = MutableLiveData(errorState)

    val isAuthLiveData: MutableLiveData<Boolean?>
        get() = repository.isAuthLiveData


    fun register(userModel: UserModel) {
        viewModelScope.launch {
            try {
                repository.register(userModel)
            } catch (api: ApiError) {
                errorState = ApiError()
            } catch (network: NetworkError) {
                errorState = NetworkError()
            } catch (unknown: UnknownError) {
                errorState = UnknownError()
            }
            errorState = null
        }
    }

    fun logIn(userModel: UserModel) {
        viewModelScope.launch {

            try {
                repository.login(userModel)
            } catch (api: ApiError) {
                errorState = ApiError()
            } catch (network: NetworkError) {
                errorState = NetworkError()
            } catch (unknown: UnknownError) {
                errorState = UnknownError()
            }
            errorState = null

        }

    }

    fun logOut() {
        repository.logOut()
    }

}