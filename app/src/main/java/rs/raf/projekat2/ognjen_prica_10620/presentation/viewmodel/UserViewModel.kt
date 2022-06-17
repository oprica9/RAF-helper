package rs.raf.projekat2.ognjen_prica_10620.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.projekat2.ognjen_prica_10620.data.models.*
import rs.raf.projekat2.ognjen_prica_10620.data.repositories.UserRepository
import rs.raf.projekat2.ognjen_prica_10620.presentation.contracts.MainContract
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.*
import timber.log.Timber
import java.lang.NullPointerException
import java.util.concurrent.TimeUnit

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel(), MainContract.UserViewModel {

    private val subscriptions = CompositeDisposable()

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }

    override val userAndRememberState: LiveData<UserAndRememberState> = MutableLiveData()
    override val rememberState: MutableLiveData<RememberState> = MutableLiveData()
    override val userState: MutableLiveData<UserState> = MutableLiveData()
    override val loginState: MutableLiveData<LoginState> = MutableLiveData()

    override fun login(username: String, pin: String) {
        if (username == "" || pin == "") {
            throw NullPointerException("Username or password can't be empty")
        }

        // Timber.e(username)
        val subscription = userRepository
            .getByUsername(username = username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (pin == it.pin) {
                        // Timber.e("POST VALUE = $it")
                        loginState.value = LoginState.Success(true)
                        // Timber.e("USER STATE VALUE = " + userState.value)
                    } else {
                        loginState.value = LoginState.Error("Wrong username or password")
                    }
                },
                {
                    //// Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getByUsername(username: String) {
        val subscription = userRepository
            .getByUsername(username = username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    //// Timber.e("$it")
                },
                {
                    //// Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAll() {
        val subscription = userRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    //// Timber.e("$it")
                },
                {
                    //// Timber.e(it)
                },
                {
                    //// Timber.e("ON COMPLETE")
                }
            )
        subscriptions.add(subscription)
    }

    override fun insertUser(user: User) {
        val subscription = userRepository
            .insertUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    //// Timber.e("COMPLETE")
                },
                {
                    //// Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }


    override fun getUserAndRemembers() {
        val subscription = userRepository
            .getUserAndRemembers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    // Timber.e("GET USER AND REMEMBERS = " + it[0])
                    it[0].rememberEntity
                    it[0].rememberEntity?.let { it1 ->
                        it[0].rememberEntity?.let { it2 ->
                            rememberState.value = RememberState.Success(
                                Remember(
                                    it2.userId, it1.rememberMe
                                )
                            )
                        }
                    }

                },
                {
                    rememberState.value = RememberState.Error("Couldnt find")
                }
            )
        subscriptions.add(subscription)
    }

    override fun insertRemembers(remember: Remember) {
        val subscription = userRepository
            .insertRemember(remember)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    //// Timber.e("COMPLETE")
                },
                {
                    //// Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getUserAndRemembersAndUpdateRemember() {
        // Timber.e("ULAZI")
        val subscription = userRepository
            .getUserAndRemembersAndUpdateRemember()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    // Timber.e("COMPLETE : $it")
                    rememberState.value = RememberState.Success(it)
                },
                {
                    // Timber.e("ERROR$it")
                }
            )
        subscriptions.add(subscription)
    }

// odavde saljemo informacije i tek kreiramo studenta u repoimpl zapravo

}