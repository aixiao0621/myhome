package az.autumn.myhome.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "temperature")
data class TemperatureEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long,
    val temperature: Float
)