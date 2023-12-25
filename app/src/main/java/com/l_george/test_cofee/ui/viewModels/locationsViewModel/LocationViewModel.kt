package com.l_george.test_cofee.ui.viewModels.locationsViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l_george.test_cofee.data.models.CoffeeShopModel
import com.l_george.test_cofee.data.repository.AuthRepository
import com.l_george.test_cofee.data.repository.LocationsRepository
import com.l_george.test_cofee.utils.ApiError
import com.l_george.test_cofee.utils.AppError
import com.l_george.test_cofee.utils.AuthError
import com.l_george.test_cofee.utils.NetworkError
import com.l_george.test_cofee.utils.UnknownError
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val repository: LocationsRepository,
    private val authRepository: AuthRepository
) :
    ViewModel() {

    private var listCoffeeShops = emptyList<CoffeeShopModel>()
        set(value) {
            field = value
            listCoffeeShopLiveData.value = value
        }

    val listCoffeeShopLiveData = MutableLiveData(listCoffeeShops)

    private var errorState: AppError? = null
        set(value) {
            field = value
            errorLiveData.value = value
        }

    val errorLiveData = MutableLiveData(errorState)


    fun getLocation() {
        viewModelScope.launch {
            try {
                errorState = null
                val list = repository.getLocations()
                listCoffeeShops = list
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

    init {
        getLocation()
    }


}