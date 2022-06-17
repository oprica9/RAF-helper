package rs.raf.projekat2.ognjen_prica_10620.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.raf.projekat2.ognjen_prica_10620.data.models.NoteEntity
import rs.raf.projekat2.ognjen_prica_10620.data.models.RememberEntity
import rs.raf.projekat2.ognjen_prica_10620.data.models.UserEntity

@Database(
    entities = [UserEntity::class, RememberEntity::class, NoteEntity::class],
    version = 4,
    exportSchema = false
)
abstract class UserDatabase() : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getNoteDao(): NoteDao
}