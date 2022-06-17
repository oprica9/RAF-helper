package rs.raf.projekat2.ognjen_prica_10620.presentation.view.states


sealed class NoteChartState {
    object Loading : NoteChartState()
    object DataFetched : NoteChartState()
    data class Success(val chartNotes: HashMap<Int, Float>) : NoteChartState()
    data class Error(val message: String) : NoteChartState()
}