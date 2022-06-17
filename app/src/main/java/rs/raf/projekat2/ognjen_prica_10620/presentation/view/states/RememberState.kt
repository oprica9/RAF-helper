package rs.raf.projekat2.ognjen_prica_10620.presentation.view.states

import rs.raf.projekat2.ognjen_prica_10620.data.models.Remember

sealed class RememberState {
    object Loading : RememberState()
    object DataFetched : RememberState()
    data class Success(val remember: Remember) : RememberState()
    data class Error(val message: String) : RememberState()
}