package rs.raf.projekat2.ognjen_prica_10620.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.HttpException
import rs.raf.projekat2.ognjen_prica_10620.data.datasources.local.LectureDao
import rs.raf.projekat2.ognjen_prica_10620.data.datasources.remote.LectureService
import rs.raf.projekat2.ognjen_prica_10620.data.models.Lecture
import rs.raf.projekat2.ognjen_prica_10620.data.models.LectureEntity
import rs.raf.projekat2.ognjen_prica_10620.data.models.Resource
import timber.log.Timber


class LectureRepositoryImpl(
    private val localDataSourceLecture: LectureDao,
    private val remoteDataSourceLecture: LectureService
) : LectureRepository {
    override fun fetchAll(): Observable<Resource<Unit>> {
        return remoteDataSourceLecture
            .getAll()
            // ovde kesiramo podatke
            .doOnNext {
                //// Timber.e("Upis u bazu")
                val entities = it.map {
                    LectureEntity(
                        subject = it.predmet,
                        type = it.tip,
                        prof = it.nastavnik,
                        groups = it.grupe,
                        day = it.dan,
                        time = it.termin,
                        room = it.ucionica
                    )
                }
                localDataSourceLecture.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
//            .onErrorReturn{
//                when(it){
//                    is HttpException -> {
//                        when(it.code()) {
//                            in 400..499 -> {
//
//                            }
//                        }
//                    }
//                }
//            }
    }

    override fun getAll(): Observable<List<Lecture>> {
        return localDataSourceLecture
            .getAll()
            .map {
                it.map {
                    Lecture(
                        it.subject,
                        it.type,
                        it.prof,
                        it.groups,
                        it.day,
                        it.time,
                        it.room
                    )
                }
            }
    }

    override fun getGroups(): Observable<List<String>> {
        return localDataSourceLecture
            .getGroups()
            .map {
                it.map {
                    it.groups
                }
            }
    }

    override fun getDays(): Observable<List<String>> {
        return localDataSourceLecture
            .getDays()
            .map {
                it.map {
                    it.day
                }
            }
    }

    override fun getAllByGroup(group: String): Observable<List<Lecture>> {
        return localDataSourceLecture
            .getAllByGroup(group)
            .map {
                it.map {
                    Lecture(
                        it.subject,
                        it.type,
                        it.prof,
                        it.groups,
                        it.day,
                        it.time,
                        it.room
                    )
                }
            }
    }

    override fun getAllByDay(day: String): Observable<List<Lecture>> {
        return localDataSourceLecture
            .getAllByDay(day)
            .map {
                it.map {
                    Lecture(
                        it.subject,
                        it.type,
                        it.prof,
                        it.groups,
                        it.day,
                        it.time,
                        it.room
                    )
                }
            }
    }

    override fun getAllByProf(prof: String): Observable<List<Lecture>> {
        return localDataSourceLecture
            .getAllByProf(prof)
            .map {
                it.map {
                    Lecture(
                        it.subject,
                        it.type,
                        it.prof,
                        it.groups,
                        it.day,
                        it.time,
                        it.room
                    )
                }
            }
    }

    override fun getAllBySubject(subject: String): Observable<List<Lecture>> {
        return localDataSourceLecture
            .getAllBySubject(subject)
            .map {
                it.map {
                    Lecture(
                        it.subject,
                        it.type,
                        it.prof,
                        it.groups,
                        it.day,
                        it.time,
                        it.room
                    )
                }
            }
    }

    override fun insert(lecture: Lecture): Completable {
        val lectureEntity = LectureEntity(
            subject = lecture.subject,
            type = lecture.type,
            prof = lecture.prof,
            groups = lecture.groups,
            day = lecture.day,
            time = lecture.time,
            room = lecture.room
        )
        return localDataSourceLecture
            .insert(lectureEntity)
    }

    override fun getFilteredData(
        group: String,
        day: String,
        prof: String,
        subj: String
    ): Observable<List<Lecture>> {
        return localDataSourceLecture
            .getFilteredData(group, day, prof)
            .map {
                it.map {
                    Lecture(
                        it.subject,
                        it.type,
                        it.prof,
                        it.groups,
                        it.day,
                        it.time,
                        it.room
                    )
                }
            }
    }

}