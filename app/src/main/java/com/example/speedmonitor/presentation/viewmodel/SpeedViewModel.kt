package com.example.speedmonitor.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedmonitor.data.repository.FleetRepository
import com.example.speedmonitor.presentation.speed.CarSpeedProvider
import com.example.speedmonitor.domain.model.SpeedEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SpeedViewModel(
    private val repository: FleetRepository,
    private val carSpeedProvider: CarSpeedProvider,
    private val customerId: String
) : ViewModel() {

    private val _speed = MutableStateFlow(0)
    val speed: StateFlow<Int> = _speed

    init {
        viewModelScope.launch {
            carSpeedProvider.observeSpeed().collect { current ->
                _speed.value = current
                val limit = repository.getCustomer(customerId).speedLimit
                if (current > limit) {
                    repository.reportSpeed(customerId, speed.value)
                }
            }
        }
    }
}
