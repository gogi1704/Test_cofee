package com.l_george.test_cofee.ui.viewModels.locationsViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l_george.test_cofee.data.models.CoffeeShopModel
import com.l_george.test_cofee.data.repository.LocationsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationViewModel @Inject constructor(private val repository: LocationsRepository) :
    ViewModel() {

    private var listCoffeeShops = emptyList<CoffeeShopModel>()
        set(value) {
            field = value
            listCoffeeShopLiveData.value = value
        }

    val listCoffeeShopLiveData = MutableLiveData(listCoffeeShops)


    fun getLocation() {
        viewModelScope.launch {
            listCoffeeShops = repository.getLocations()
        }
    }
}