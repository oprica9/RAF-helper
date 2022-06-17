package rs.raf.projekat2.ognjen_prica_10620.presentation.view.states

import rs.raf.projekat2.ognjen_prica_10620.data.models.Note

sealed class NoteEditState {
    object Loading : NoteEditState()
    object DataFetched : NoteEditState()
    data class Success(val note: Note) : NoteEditState()
    data class Error(val message: String) : NoteEditState()
}