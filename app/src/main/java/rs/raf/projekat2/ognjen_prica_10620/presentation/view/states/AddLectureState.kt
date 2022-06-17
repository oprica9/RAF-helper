package rs.raf.projekat2.ognjen_prica_10620.presentation.view.states

sealed class AddLectureState {
    object Success : AddLectureState()
    data class Error(val message: String) : AddLectureState()
}