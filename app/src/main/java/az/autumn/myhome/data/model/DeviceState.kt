package az.autumn.myhome.data.model

data class DeviceState(
    val light1: Boolean,
    val light2: Boolean,
    val door: Boolean,
    val temp: String
)