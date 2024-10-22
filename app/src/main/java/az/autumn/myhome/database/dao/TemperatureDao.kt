package az.autumn.myhome.database.dao

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import az.autumn.myhome.database.entity.TemperatureEntity
import kotlinx.coroutines.flow.Flow

@Dao
@Entity
interface TemperatureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemperature(temperature: TemperatureEntity)

    @Query("SELECT * FROM temperature ORDER BY timestamp DESC")
    fun getAllTemperatures(): Flow<List<TemperatureEntity>>
}