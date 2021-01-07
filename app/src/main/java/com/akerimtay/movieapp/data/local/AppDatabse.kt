package com.akerimtay.movieapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.akerimtay.movieapp.data.local.dao.MovieDao
import com.akerimtay.movieapp.data.local.entity.CategoryAndMovieEntity
import com.akerimtay.movieapp.data.local.entity.CategoryEntity
import com.akerimtay.movieapp.data.local.entity.MovieEntity
import com.akerimtay.movieapp.data.local.mapping.DateConverter
import com.akerimtay.movieapp.data.local.mapping.MovieTypeConverter

@Database(
    entities = [(MovieEntity::class), (CategoryEntity::class), (CategoryAndMovieEntity::class)],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [(DateConverter::class), (MovieTypeConverter::class)])
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "movie_app_db")
                .fallbackToDestructiveMigration()
                .fallbackToDestructiveMigrationOnDowngrade()
                .build()
    }

}