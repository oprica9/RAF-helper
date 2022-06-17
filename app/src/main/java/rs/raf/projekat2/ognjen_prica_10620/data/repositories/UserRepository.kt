package rs.raf.projekat2.ognjen_prica_10620.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import rs.raf.projekat2.ognjen_prica_10620.data.models.*

interface UserRepository {

    fun getAll(): Observable<List<User>>
    fun getByUsername(username: String): Observable<User>
    fun getById(id: Long): Observable<User>
    fun insertUser(user: User): Completable
    fun insertRemember(remember: Remember): Completable
    fun getUserAndRemembers(): Observable<List<UserAndRemember>>
    fun getRemember(id: Long) : Observable<Remember>
    fun updateRemember(id: Long, value: Long) : Completable
    fun getUserAndRemembersAndUpdateRemember(): Observable<Remember>

}