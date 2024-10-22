package az.autumn.myhome.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import az.autumn.myhome.data.model.DeviceState
import az.autumn.myhome.data.repository.DeviceRepository
import az.autumn.myhome.database.dao.TemperatureDao
import az.autumn.myhome.database.entity.TemperatureEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val deviceRepository: DeviceRepository,
    private val temperatureDao: TemperatureDao
) : ViewModel() {

    private val _deviceState = MutableStateFlow<DeviceState?>(null)
    val deviceState: StateFlow<DeviceState?> = _deviceState.asStateFlow()

    val allTemperatures: Flow<List<TemperatureEntity>> = temperatureDao.getAllTemperatures()

    init {
        fetchDeviceState()
    }

    fun fetchDeviceState() {
        viewModelScope.launch {
            try {
                val state = deviceRepository.getDeviceState()
                _deviceState.value = state

                // Save temperature to database
                val temperatureEntity = TemperatureEntity(
                    timestamp = System.currentTimeMillis(),
                    temperature = state.temp.toFloat()
                )
                temperatureDao.insertTemperature(temperatureEntity)

            } catch (e: Exception) {
                // Handle exceptions
            }
        }
    }

    fun updateDeviceState(deviceState: DeviceState) {
        viewModelScope.launch {
            try {
                deviceRepository.updateDeviceState(deviceState)
                _deviceState.value = deviceState
            } catch (e: Exception) {
                // Handle exceptions
            }
        }
    }
}

class HomeViewModelFactory(
    private val deviceRepository: DeviceRepository,
    private val temperatureDao: TemperatureDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(deviceRepository, temperatureDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}