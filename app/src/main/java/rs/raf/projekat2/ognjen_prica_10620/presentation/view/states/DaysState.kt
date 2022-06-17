package rs.raf.projekat2.ognjen_prica_10620.presentation.view.states

sealed class DaysState {
    object Loading : DaysState()
    object DataFetched : DaysState()
    data class Success(val days: List<String>) : DaysState()
    data class Error(val message: String) : DaysState()
}