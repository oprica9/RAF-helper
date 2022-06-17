package rs.raf.projekat2.ognjen_prica_10620.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.ognjen_prica_10620.data.models.LectureEntity

@Dao
interface LectureDao {

    @Query("SELECT * FROM lectures")
    fun getAll(): Observable<List<LectureEntity>>

    @Query("SELECT * FROM lectures GROUP BY groups")
    fun getGroups(): Observable<List<LectureEntity>>

    @Query("SELECT * FROM lectures GROUP BY day")
    fun getDays(): Observable<List<LectureEntity>>

    @Query("SELECT * FROM lectures WHERE groups LIKE '%' || :group || '%'")
    fun getAllByGroup(group: String): Observable<List<LectureEntity>>

    @Query("SELECT * FROM lectures WHERE day LIKE :day")
    fun getAllByDay(day: String): Observable<List<LectureEntity>>

    @Query("SELECT * FROM lectures WHERE prof LIKE '%' || :prof || '%'")
    fun getAllByProf(prof: String): Observable<List<LectureEntity>>

    @Query("SELECT * FROM lectures WHERE subject LIKE :subject")
    fun getAllBySubject(subject: String): Observable<List<LectureEntity>>

    @Query("DELETE FROM lectures")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lectureEntity: LectureEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<LectureEntity>): Completable

    @Transaction
    fun deleteAndInsertAll(entities: List<LectureEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

    @Query("SELECT * FROM lectures WHERE groups LIKE '%' || :group || '%' AND day LIKE '%' || :day || '%' AND (prof LIKE :prof || '%' OR subject LIKE '%' || :prof || '%')")
    fun getFilteredData(
        group: String,
        day: String,
        prof: String,
    ): Observable<List<LectureEntity>>

}