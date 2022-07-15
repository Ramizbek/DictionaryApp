package ramizbek.aliyev.dictionaryapp.dao

import androidx.room.*
import ramizbek.aliyev.dictionaryapp.entity.Categoriya


@Dao
interface CategoriyaInterface {

    @Transaction
    @Query("select * from Categoriya")
    fun getCategory(): List<Categoriya>

    @Insert
    fun addCategory(categoriya: Categoriya)

    @Update()
    fun updateCategory(categoriya: Categoriya)

    @Delete
    fun deleteCategory(categoriya: Categoriya)


}