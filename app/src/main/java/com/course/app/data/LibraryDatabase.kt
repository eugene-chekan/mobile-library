package com.course.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [LibraryItemEntity::class],
    version = 1,
    exportSchema = false   // set to true in production to track schema history
)
@TypeConverters(Converters::class)
abstract class LibraryDatabase : RoomDatabase() {

    abstract fun libraryDao(): LibraryDao

    companion object {

        @Volatile  // writes are immediately visible to all threads
        private var INSTANCE: LibraryDatabase? = null

        fun getInstance(context: Context): LibraryDatabase {
            // Return existing instance fast (no lock needed after first creation)
            return INSTANCE ?: synchronized(this) {
                // Second check inside the lock: another thread may have
                // initialised it while we were waiting.
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,  // never pass Activity context here
                    LibraryDatabase::class.java,
                    "library_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
