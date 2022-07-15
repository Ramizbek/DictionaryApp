package ramizbek.aliyev.dictionaryapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(indices = [Index(value = ["name1"], unique = true)])
class Lugat : Serializable {

    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "name1")

    var id: Int? = null
    var rasim: String? = null
    var tanlanganKategoriya: String? = null
    var soz: String? = null
    var tarjima: String? = null
    var kichikMalumot: String? = null
    var like: String? = null

    constructor(
        id: Int?,
        rasim: String?,
        tanlanganKategoriya: String?,
        soz: String?,
        tarjima: String?,
        kichikMalumot: String?,
        like: String?
    ) {
        this.id = id
        this.rasim = rasim
        this.tanlanganKategoriya = tanlanganKategoriya
        this.soz = soz
        this.tarjima = tarjima
        this.kichikMalumot = kichikMalumot
        this.like = like
    }
}