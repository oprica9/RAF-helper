package rs.raf.projekat2.ognjen_prica_10620.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.ognjen_prica_10620.data.datasources.local.NoteDao
import rs.raf.projekat2.ognjen_prica_10620.data.models.*
import timber.log.Timber

class NoteRepositoryImpl(
    private val noteDao: NoteDao,
) : NoteRepository {
    override fun getAll(): Observable<List<Note>> {
        return noteDao.getAll().map {
            it.map {
                Note(
                    id = it.id,
                    content = it.content,
                    title = it.title,
                    archived = it.archived,
                    dateCreated = it.dateCreated
                )
            }
        }
    }

    override fun getById(id: Long): Observable<Note> {
        return noteDao.getById(id).map {
            Note(
                id = it.id,
                title = it.title,
                content = it.content,
                archived = it.archived,
                dateCreated = it.dateCreated
            )
        }
    }

    override fun insertNote(note: Note): Completable {
        val noteEntity = NoteEntity(
            id = note.id,
            title = note.title,
            content = note.content,
            archived = note.archived,
            dateCreated = note.dateCreated
        )
        val test = noteDao.insert(noteEntity)
        // Timber.e("INSERT NoteEntity = $noteEntity")
        // Timber.e("INSERT NoteDao.insert() = $test")
        return test
    }

    override fun deleteNote(id: Long): Completable {
        return noteDao.deleteNote(id)
    }

    override fun updateNote(note: Note): Completable {
        return noteDao.updateNote(
            NoteEntity(
                id = note.id,
                content = note.content,
                archived = note.archived,
                title = note.title,
                dateCreated = note.dateCreated
            )
        )
    }

    override fun archive(id: Long, archived: Int): Completable {
        return noteDao.archive(id, archived)
    }

    override fun getLastDaysNoteNum(): Observable<List<Note>> {
        return noteDao.getLastDaysNoteNum().map {
            it.map {
                Note(
                    id = it.id,
                    title = it.title,
                    content = it.content,
                    archived = it.archived,
                    dateCreated = it.dateCreated
                )
            }
        }
    }

    override fun getFilteredData(
        titleContent: String,
        archived: Int
    ): Observable<List<Note>> {
        return noteDao
            .getFilteredData(titleContent, archived)
            .map {
                it.map {
                    Note(
                        it.id,
                        it.title,
                        it.content,
                        it.archived,
                        dateCreated = it.dateCreated
                    )
                }
            }
    }

}