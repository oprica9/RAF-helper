package rs.raf.projekat2.ognjen_prica_10620.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.ognjen_prica_10620.data.models.Lecture
import rs.raf.projekat2.ognjen_prica_10620.data.models.Note
import rs.raf.projekat2.ognjen_prica_10620.data.models.User

interface NoteRepository {

    fun getAll(): Observable<List<Note>>
    fun getById(id: Long): Observable<Note>
    fun insertNote(note: Note): Completable
    fun deleteNote(id: Long): Completable
    fun updateNote(note: Note): Completable
    fun archive(id: Long, archived: Int): Completable
    fun getLastDaysNoteNum(): Observable<List<Note>>

    fun getFilteredData(
        titleContent: String,
        archived: Int
    ): Observable<List<Note>>
}