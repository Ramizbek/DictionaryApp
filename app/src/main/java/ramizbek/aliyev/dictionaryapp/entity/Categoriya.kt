package ramizbek.aliyev.dictionaryapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(indices = [Index(value = ["name"], unique = true)])
class Categoriya : Serializable {

    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "name")

    var id: Int? = null
    var kategoriyaNomi: String? = null


    constructor(
        id: Int?,
        kategoriyaNomi: String?,
    ) {
        this.id = id
        this.kategoriyaNomi = kategoriyaNomi
    }


}