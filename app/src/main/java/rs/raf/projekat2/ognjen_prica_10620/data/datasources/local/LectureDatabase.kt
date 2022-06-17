package rs.raf.projekat2.ognjen_prica_10620.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.raf.projekat2.ognjen_prica_10620.data.models.LectureEntity

@Database(
    entities = [LectureEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LectureDatabase() : RoomDatabase() {
    abstract fun getLectureDao(): LectureDao
}