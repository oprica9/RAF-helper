package rs.raf.projekat2.ognjen_prica_10620.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long? = null,
    val username: String,
    val pin: String,
)