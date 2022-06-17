package rs.raf.projekat2.ognjen_prica_10620.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.ognjen_prica_10620.data.models.RememberEntity
import rs.raf.projekat2.ognjen_prica_10620.data.models.UserAndRemember
import rs.raf.projekat2.ognjen_prica_10620.data.models.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAll(): Observable<List<UserEntity>>

    @Query("SELECT * FROM users WHERE username=:username")
    fun getByUsername(username: String): Observable<UserEntity>

    @Query("SELECT * FROM users WHERE uid=:id")
    fun getById(id: Long): Observable<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRemember(rememberEntity: RememberEntity): Completable

    @Transaction
    @Query("SELECT * FROM users")
    fun getUserAndRemembers(): Observable<List<UserAndRemember>>

    @Query("UPDATE remember SET rememberMe=:rememberMe where id=:id")
    fun updateRemember(id: Long, rememberMe: Int): Completable

    @Transaction
    fun getUserAndRemembersAndUpdateRemember(value: Int, entities: List<UserAndRemember>) {
        getUserAndRemembers()
        entities[0].rememberEntity?.id?.let { updateRemember(it, value).blockingAwait() }

    }

    @Query("SELECT * FROM remember where id=:id")
    fun getRemember(id: Long): Observable<RememberEntity>

}
