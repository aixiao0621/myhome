package az.autumn.myhome.network

import az.autumn.myhome.data.model.DeviceState
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("device_state")
    suspend fun getDeviceState(): DeviceState

    @POST("device_state")
    suspend fun updateDeviceState(@Body deviceState: DeviceState)
}