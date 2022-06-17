package rs.raf.projekat2.ognjen_prica_10620.presentation.view.states

sealed class LoginState {
    object Loading : LoginState()
    object DataFetched : LoginState()
    data class Success(val loggedIn: Boolean) : LoginState()
    data class Error(val message: String) : LoginState()
}