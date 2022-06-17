package rs.raf.projekat2.ognjen_prica_10620.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.ognjen_prica_10620.data.datasources.local.UserDao
import rs.raf.projekat2.ognjen_prica_10620.data.models.*
import timber.log.Timber

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override fun getAll(): Observable<List<User>> {
        return userDao
            .getAll()
            .map {
                it.map {
                    User(
                        username = it.username,
                        pin = it.pin,
                    )
                }
            }
    }

    override fun getByUsername(username: String): Observable<User> {
        return userDao
            .getByUsername(username)
            .map {
                //// Timber.e(it.toString())
                User(
                    username = it.username,
                    pin = it.pin,
                )
            }
    }

    override fun getById(id: Long): Observable<User> {
        return userDao
            .getById(id = id)
            .map {
                User(
                    username = it.username,
                    pin = it.pin,
                )
            }
    }

    override fun insertUser(user: User): Completable {
        val userEntity = UserEntity(
            username = user.username,
            pin = user.pin
        )
        return userDao.insertUser(userEntity)
    }

    override fun insertRemember(remember: Remember): Completable {
        val rememberEntity = RememberEntity(
            userId = remember.userId,
            rememberMe = remember.rememberMe
        )
        return userDao.insertRemember(rememberEntity)
    }

    override fun getUserAndRemembers(): Observable<List<UserAndRemember>> {
        return userDao.getUserAndRemembers()
    }


    override fun updateRemember(id: Long, value: Long): Completable {
        //// Timber.e("VREDNOST = $id")
        return userDao.updateRemember(id, value.toInt())
    }

    override fun getUserAndRemembersAndUpdateRemember(): Observable<Remember> {
        return userDao.getUserAndRemembers()
            .doOnNext {
                val entities = it.map {
                    UserAndRemember(
                        it.userEntity,
                        it.rememberEntity
                    )
                }
                var value = 1
//                if(entities[0].rememberEntity?.rememberMe == false){
//                    value = 1
//                }
                userDao.getUserAndRemembersAndUpdateRemember(value, entities)
            }.map {
                it[0].rememberEntity?.let { it1 ->
                    it[0].rememberEntity?.let { it2 ->
                        Remember(
                            it2.userId,
                            it1.rememberMe,

                            )
                    }
                }
            }
    }

    override fun getRemember(id: Long): Observable<Remember> {
        val test = userDao.getRemember(id)
        val remember = test.map {
            Remember(
                userId = it.userId,
                rememberMe = it.rememberMe
            )
        }
        //// Timber.e("VREDNOST GET = $remember")
        return remember
    }


}