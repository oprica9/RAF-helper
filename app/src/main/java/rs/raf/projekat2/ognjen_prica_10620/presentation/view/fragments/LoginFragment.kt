package rs.raf.projekat2.ognjen_prica_10620.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.ognjen_prica_10620.R
import rs.raf.projekat2.ognjen_prica_10620.data.models.LoginData
import rs.raf.projekat2.ognjen_prica_10620.presentation.composable.LoginScreen
import rs.raf.projekat2.ognjen_prica_10620.presentation.contracts.MainContract
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.activities.MainActivity.Companion.MAIN_FRAGMENT_TAG
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.LoginState
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.UserAndRememberState
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.UserState
import rs.raf.projekat2.ognjen_prica_10620.presentation.viewmodel.UserViewModel
import timber.log.Timber
import java.lang.NullPointerException

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val userViewModel: MainContract.UserViewModel by viewModel<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = ComposeView(requireContext())
        view.apply {
            setContent {
                LoginScreen() {
                    login(it)
                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initObservers()
    }

    private fun initObservers() {
        userViewModel.loginState.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })

    }

    private fun renderState(state: LoginState?) {
        // Timber.e("RENDER LOGIN")
        when (state) {

            is LoginState.Success -> {
                // Timber.e("SUCCESS?")
                userViewModel.getUserAndRemembersAndUpdateRemember()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fcvMain, MainFragment(), MAIN_FRAGMENT_TAG)
                transaction.commit()
            }
            is LoginState.Error -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                    .show()
            }

            else -> {
                Toast.makeText(requireContext(), "USPEO SI NESTO NEMOGUCE", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun login(loginData: LoginData) {
        // Timber.e("LOGIN?")
        try {
            userViewModel.login(loginData.username, loginData.pin)
        } catch (e: NullPointerException) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT)
                .show()
        }

    }


}