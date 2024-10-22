package az.autumn.myhome.data.repository

import az.autumn.myhome.data.model.DeviceState
import az.autumn.myhome.network.ApiService

class DeviceRepository(private val apiService: ApiService) {

    suspend fun getDeviceState(): DeviceState {
        return apiService.getDeviceState()
    }

    suspend fun updateDeviceState(deviceState: DeviceState) {
        apiService.updateDeviceState(deviceState)
    }
}