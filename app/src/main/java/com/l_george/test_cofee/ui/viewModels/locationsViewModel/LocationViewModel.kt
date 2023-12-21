package com.l_george.test_cofee.ui.viewModels.locationsViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l_george.test_cofee.data.repository.LocationsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationViewModel @Inject constructor(private val repository: LocationsRepository) :
    ViewModel() {

        fun getLocation(){
            viewModelScope.launch {
                repository.getLocations()
            }
        }
}