package rs.raf.projekat2.ognjen_prica_10620.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.ognjen_prica_10620.data.models.LectureEntity
import rs.raf.projekat2.ognjen_prica_10620.data.models.Note
import rs.raf.projekat2.ognjen_prica_10620.data.models.NoteEntity

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getAll(): Observable<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id=:id")
    fun getById(id: Long): Observable<NoteEntity>

    @Query("DELETE FROM notes WHERE id=:id")
    fun deleteNote(id: Long): Completable

    @Query("DELETE FROM notes")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(noteEntity: NoteEntity): Completable

    @Query("SELECT * FROM notes WHERE (title LIKE :titleContent || '%' OR content LIKE '%' || :titleContent || '%') AND archived=:archived")
    fun getFilteredData(
        titleContent: String,
        archived: Int

    ): Observable<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE dateCreated <= 5")
    fun getLastDaysNoteNum(): Observable<List<NoteEntity>>

    @Query("UPDATE notes SET archived=:archived where id=:id")
    fun archive(id: Long, archived: Int): Completable

    @Update
    fun updateNote(note: NoteEntity): Completable

}