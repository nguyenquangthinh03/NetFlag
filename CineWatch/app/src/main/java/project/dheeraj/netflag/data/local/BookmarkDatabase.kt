package project.dheeraj.netflag.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import project.dheeraj.netflag.data.local.dao.BookmarkDao
import project.dheeraj.netflag.data.model.MovieDB


@Database(
    entities = [MovieDB::class],
    version = 1, exportSchema = false
)
abstract class BookmarkDatabase : RoomDatabase() {

    abstract fun getBookmarkDao() : BookmarkDao

    companion object {

        const val DATABASE_NAME = "BOOKMARK_DATABASE"

        @Volatile
        private var INSTANCE : BookmarkDatabase? = null

        fun getInstance(context : Context) : BookmarkDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }

    }

}