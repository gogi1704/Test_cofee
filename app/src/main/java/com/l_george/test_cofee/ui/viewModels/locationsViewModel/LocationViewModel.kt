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
import java.lang.Math.sin
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sqrt

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

    fun setLocation(myPoint: Point?) {
        viewModelScope.launch {
            if (myPoint != null) {
                listCoffeeShops = repository.getLocations().map {
                    it.copy(distanceFromMe = "${calculateDistance(myPoint, it.point)} км")
                }
            }else{
                getLocation()
            }
        }

    }

    private fun calculateDistance(point1: Point, point2: Point): Double {
        val earthRadius = 6371
        val latDiff = Math.toRadians(point2.latitude - point1.latitude)
        val lonDiff = Math.toRadians(point2.longitude - point1.longitude)
        val a = sin(latDiff / 2).pow(2.0) + cos(Math.toRadians(point1.latitude)) * cos(
            Math.toRadians(point2.latitude)
        ) * sin(lonDiff / 2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadius * c
    }

//    init {
//        getLocation()
//    }


}