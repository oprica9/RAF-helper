package rs.raf.projekat2.ognjen_prica_10620.presentation.view.states

import rs.raf.projekat2.ognjen_prica_10620.data.models.UserAndRemember

sealed class UserAndRememberState {
    object Loading : UserAndRememberState()
    object DataFetched : UserAndRememberState()
    data class Success(val user: UserAndRemember) : UserAndRememberState()
    data class Error(val message: String) : UserAndRememberState()
}