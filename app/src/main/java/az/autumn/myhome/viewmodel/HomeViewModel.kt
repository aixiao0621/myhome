package az.autumn.myhome.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import az.autumn.myhome.data.model.DeviceState
import az.autumn.myhome.data.repository.DeviceRepository
import az.autumn.myhome.database.dao.TemperatureDao
import az.autumn.myhome.database.entity.TemperatureEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(
    private val deviceRepository: DeviceRepository,
    private val temperatureDao: TemperatureDao
) : ViewModel() {

    private val _deviceState = MutableStateFlow<DeviceState?>(null)
    val deviceState: StateFlow<DeviceState?> = _deviceState.asStateFlow()

    val allTemperatures: Flow<List<TemperatureEntity>> = temperatureDao.getAllTemperatures()

    init {
        viewModelScope.launch {
            while (isActive) {
                fetchDeviceState()
                delay(60000) // Poll every 60 seconds
            }
        }
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

            } catch (e: IOException) {
                Log.d("TAG", "fetchDeviceState: ${e.message}")
            } catch (e: HttpException){
                Log.d("TAG", "fetchDeviceState: ${e.message}")
            }
        }
    }

    fun updateDeviceState(deviceState: DeviceState) {
        viewModelScope.launch {
            try {
                deviceRepository.updateDeviceState(deviceState)
                _deviceState.value = deviceState
            } catch (e: IOException) {
                Log.d("fe", "updateDeviceState: ${e.message}")
            } catch (e: HttpException) {
                Log.d("fe_http", "updateDeviceState: ${e.message}")
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