package rs.raf.projekat2.ognjen_prica_10620.presentation.view.states

import rs.raf.projekat2.ognjen_prica_10620.data.models.Lecture

sealed class LecturesState {
    object Loading : LecturesState()
    object DataFetched : LecturesState()
    data class Success(val lectures: List<Lecture>) : LecturesState()
    data class Error(val message: String) : LecturesState()
}