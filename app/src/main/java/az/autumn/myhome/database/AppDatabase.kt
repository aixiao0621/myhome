package az.autumn.myhome.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import az.autumn.myhome.database.dao.TemperatureDao
import az.autumn.myhome.database.entity.TemperatureEntity

@Database(entities = [TemperatureEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun temperatureDao(): TemperatureDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "home_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}