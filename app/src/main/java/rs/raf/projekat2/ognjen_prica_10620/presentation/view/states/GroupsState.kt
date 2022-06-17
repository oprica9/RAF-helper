package rs.raf.projekat2.ognjen_prica_10620.presentation.view.states

sealed class GroupsState {
    object Loading : GroupsState()
    object DataFetched : GroupsState()
    data class Success(val groups: List<String>) : GroupsState()
    data class Error(val message: String) : GroupsState()
}