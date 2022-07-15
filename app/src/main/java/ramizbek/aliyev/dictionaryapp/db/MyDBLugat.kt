package ramizbek.aliyev.dictionaryapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ramizbek.aliyev.dictionaryapp.dao.CategoriyaInterfaceLugat
import ramizbek.aliyev.dictionaryapp.entity.Lugat

@Database(entities = [Lugat::class], version = 1)
abstract class MyDBLugat : RoomDatabase() {

    abstract fun categoryDaoLugat(): CategoriyaInterfaceLugat

    companion object {
        private var instance: MyDBLugat? = null

        @Synchronized
        fun getInstance(context: Context): MyDBLugat {
            if (instance == null) {
                instance = Room.databaseBuilder(context, MyDBLugat::class.java, "news_db1")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}