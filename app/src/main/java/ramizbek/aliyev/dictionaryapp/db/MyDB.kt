package ramizbek.aliyev.dictionaryapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ramizbek.aliyev.dictionaryapp.dao.CategoriyaInterface
import ramizbek.aliyev.dictionaryapp.entity.Categoriya

@Database(entities = [Categoriya::class], version = 1)
abstract class MyDB : RoomDatabase() {

    abstract fun categoryDao(): CategoriyaInterface

    companion object {
        private var instance: MyDB? = null

        @Synchronized
        fun getInstance(context: Context): MyDB {
            if (instance == null) {
                instance = Room.databaseBuilder(context, MyDB::class.java, "news_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}