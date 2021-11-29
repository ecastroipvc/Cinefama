package com.pm.cinefama.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pm.cinefama.data.dao.FilmeDao
import com.pm.cinefama.data.entities.Filme

@Database(entities = [Filme :: class], version = 3, exportSchema = true, autoMigrations = [AutoMigration (from = 3, to = 4)])
abstract class FilmeDatabase : RoomDatabase() {

    abstract fun filmeDao() : FilmeDao
    companion object {
        @Volatile
        private var INSTANCE: FilmeDatabase? = null

        fun getDatabase(context: Context): FilmeDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FilmeDatabase::class.java,
                    "filme_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}