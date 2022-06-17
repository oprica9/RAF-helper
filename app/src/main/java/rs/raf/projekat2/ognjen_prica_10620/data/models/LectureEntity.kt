package rs.raf.projekat2.ognjen_prica_10620.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lectures")
data class LectureEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val subject: String,
    val type: String,
    val prof: String,
    val groups: String,
    val day: String,
    val time: String,
    val room: String
)