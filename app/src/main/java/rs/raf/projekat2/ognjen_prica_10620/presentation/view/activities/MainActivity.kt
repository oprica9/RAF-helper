package rs.raf.projekat2.ognjen_prica_10620.presentation.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.ognjen_prica_10620.R
import rs.raf.projekat2.ognjen_prica_10620.data.models.Remember
import rs.raf.projekat2.ognjen_prica_10620.data.models.RememberEntity
import rs.raf.projekat2.ognjen_prica_10620.data.models.User
import rs.raf.projekat2.ognjen_prica_10620.data.models.UserEntity
import rs.raf.projekat2.ognjen_prica_10620.presentation.contracts.MainContract
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.fragments.AddNoteFragment
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.fragments.LoginFragment
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.fragments.MainFragment
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.RememberState
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.UserAndRememberState
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.UserState
import rs.raf.projekat2.ognjen_prica_10620.presentation.viewmodel.LectureViewModel
import rs.raf.projekat2.ognjen_prica_10620.presentation.viewmodel.UserViewModel
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private val lectureViewModel: MainContract.LectureViewModel by viewModel<LectureViewModel>()
    private val userViewModel: MainContract.UserViewModel by viewModel<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                init()
            }
        }
        setContentView(R.layout.activity_main)
    }

    private fun init(): Boolean {
        // Timber.e("MAIN ACTIVITY INIT")
        //initData()
        initDataObservers()

        return false
    }

    private fun initDataObservers() {
        userViewModel.rememberState.observe(this, Observer {
            renderState(it)
        })
        userViewModel.userState.observe(this, Observer {
            renderStateUser(it)
        })
        userViewModel.getUserAndRemembers()
    }

    private fun renderStateUser(state: UserState?) {
//// Timber.e("LOL")
        val transaction = supportFragmentManager.beginTransaction()
        when (state) {
            is UserState.Success -> {
                transaction.replace(
                    R.id.fcvMain,
                    MainFragment(),
                    Companion.MAIN_FRAGMENT_TAG
                )
            }
            else -> {
                // Timber.e("SOMETHING HAPPENED")
            }
        }
        transaction.commit()
    }

    private fun renderState(state: RememberState) {
        //// Timber.e("LOL")
        val transaction = supportFragmentManager.beginTransaction()
        when (state) {
            is RememberState.Success -> {
                // Timber.e("MAIN ACTIVITY SUCCESS = " + state.remember.rememberMe)
                if (state.remember.rememberMe) {
                    transaction.replace(
                        R.id.fcvMain,
                        MainFragment(),
                        Companion.MAIN_FRAGMENT_TAG
                    )
                } else {
                    transaction.replace(
                        R.id.fcvMain,
                        LoginFragment(),
                        Companion.LOGIN_FRAGMENT_TAG
                    )
                }
            }

            else -> {
                // Timber.e("SOMETHING HAPPENED")
            }
        }
        transaction.commit()
    }

    private fun initData() {

        val userEntity = UserEntity(
            uid = 1,
            username = "ogi",
            pin = "123"
        )
        val rememberEntity = userEntity.uid?.let {
            RememberEntity(
                userId = 1,
                rememberMe = false
            )
        }

        //// Timber.e("MAINNN")
        //// Timber.e(userEntity.toString())
        //// Timber.e(rememberEntity.toString())

        userViewModel.insertUser(User(userEntity.username, userEntity.pin))

        if (rememberEntity != null) {
            userViewModel.insertRemembers(
                Remember(
                    rememberEntity.userId,
                    rememberEntity.rememberMe
                )
            )
        }

    }

    companion object {
        const val MAIN_FRAGMENT_TAG = "main_fragment_tag"
        const val LOGIN_FRAGMENT_TAG = "login_fragment_tag"
    }

    fun replaceFragments(curr: Fragment, next: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if (curr is AddNoteFragment) {
            supportFragmentManager.popBackStackImmediate()
        }
        transaction.replace(R.id.fcvMain, next, MAIN_FRAGMENT_TAG)
        transaction.commit()
    }

}