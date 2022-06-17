package rs.raf.projekat2.ognjen_prica_10620.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String,
    val content: String,
    val archived: Boolean,
    val dateCreated: Int
)