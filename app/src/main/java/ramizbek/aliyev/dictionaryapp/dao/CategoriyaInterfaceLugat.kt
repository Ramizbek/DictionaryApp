package ramizbek.aliyev.dictionaryapp.dao

import androidx.room.*
import ramizbek.aliyev.dictionaryapp.entity.Lugat

@Dao
interface CategoriyaInterfaceLugat {

    @Transaction
    @Query("select * from Lugat")
    fun getLugat(): List<Lugat>

    @Insert
    fun addLugat(lugat: Lugat)

    @Update()
    fun updateLugat(lugat: Lugat)

    @Delete
    fun deleteLugat(lugat: Lugat)


}