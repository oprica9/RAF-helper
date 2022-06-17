package rs.raf.projekat2.ognjen_prica_10620.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.ognjen_prica_10620.data.models.Lecture
import rs.raf.projekat2.ognjen_prica_10620.data.models.Resource

interface LectureRepository {

    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Lecture>>
    fun getGroups(): Observable<List<String>>
    fun getDays(): Observable<List<String>>
    fun getAllByGroup(group: String): Observable<List<Lecture>>
    fun getAllByDay(day: String): Observable<List<Lecture>>
    fun getAllByProf(prof: String): Observable<List<Lecture>>
    fun getAllBySubject(subject: String): Observable<List<Lecture>>
    fun insert(lecture: Lecture): Completable

    fun getFilteredData(
        group: String,
        day: String,
        prof: String,
        subj: String
    ): Observable<List<Lecture>>

}