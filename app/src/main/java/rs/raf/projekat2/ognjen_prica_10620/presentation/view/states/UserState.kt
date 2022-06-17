package rs.raf.projekat2.ognjen_prica_10620.presentation.view.states

import rs.raf.projekat2.ognjen_prica_10620.data.models.User

sealed class UserState {
    object Loading : UserState()
    object DataFetched : UserState()
    data class Success(val user: User) : UserState()
    data class Error(val message: String) : UserState()
}