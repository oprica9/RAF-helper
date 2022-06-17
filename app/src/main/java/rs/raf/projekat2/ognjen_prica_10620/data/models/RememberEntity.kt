package rs.raf.projekat2.ognjen_prica_10620.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remember")
data class RememberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val userId: Long,
    val rememberMe: Boolean = false
)