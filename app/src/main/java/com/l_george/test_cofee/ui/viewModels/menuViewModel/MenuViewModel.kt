package com.l_george.test_cofee.ui.viewModels.menuViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l_george.test_cofee.data.models.MenuModel
import com.l_george.test_cofee.data.repository.AuthRepository
import com.l_george.test_cofee.data.repository.MenuRepository
import com.l_george.test_cofee.utils.ApiError
import com.l_george.test_cofee.utils.AppError
import com.l_george.test_cofee.utils.AuthError
import com.l_george.test_cofee.utils.NetworkError
import com.l_george.test_cofee.utils.UnknownError
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var errorState: AppError? = null
        set(value) {
            field = value
            errorLiveData.value = value
        }

    val errorLiveData = MutableLiveData(errorState)

    private var menuList = emptyList<MenuModel>()
        set(value) {
            field = value
            menuListLiveData.value = value
        }
    val menuListLiveData = MutableLiveData(menuList)

    private var bagList = menuRepository.getResult()
        set(value) {
            field = value
            bagListLiveData.value = value
        }
    val bagListLiveData = MutableLiveData(bagList)

    fun getMenuById(id: Int) {
        viewModelScope.launch {
            try {
                menuList = menuRepository.getMenuByLocationId(id)
            } catch (api: ApiError) {
                errorState = ApiError()
            } catch (network: NetworkError) {
                errorState = NetworkError()
            } catch (auth: AuthError) {
                authRepository.logOut()
                errorState = AuthError()
            } catch (unknown: UnknownError) {
                errorState = UnknownError()
            }
            errorState = null
        }

    }

    fun addToBag(menuModel: MenuModel) {
        menuRepository.addToBag(menuModel)
        bagList = menuRepository.getResult()
    }

    fun removeFromBag(menuModel: MenuModel) {
        menuRepository.removeFromBag(menuModel)
        bagList = menuRepository.getResult()
    }

    fun resetBag() {
        menuRepository.resetBag()
    }

}