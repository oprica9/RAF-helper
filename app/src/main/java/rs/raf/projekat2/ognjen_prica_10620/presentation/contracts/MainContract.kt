package rs.raf.projekat2.ognjen_prica_10620.presentation.contracts

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import rs.raf.projekat2.ognjen_prica_10620.data.models.*
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.*

interface MainContract {

    interface LectureViewModel {
        val lecturesState: LiveData<LecturesState>
        val addDone: LiveData<AddLectureState>
        val groups: LiveData<GroupsState>
        val days: LiveData<DaysState>

        fun fetchAllLectures()
        fun getAllLectures()
        fun getAllGroups()
        fun getAllDays()
        fun getFilteredData(list: List<String>)
    }

    interface UserViewModel {

        val userAndRememberState: LiveData<UserAndRememberState>
        val rememberState: LiveData<RememberState>
        val userState: LiveData<UserState>
        val loginState: LiveData<LoginState>

        fun login(username: String, pin: String)
        fun getByUsername(username: String)
        fun getAll()
        fun insertUser(user: User)
        fun getUserAndRemembers()
        fun insertRemembers(remember: Remember)
        fun getUserAndRemembersAndUpdateRemember()
    }

    interface NoteViewModel {

        val test: List<NoteChartState>
        val item: MutableState<List<NoteChartState>>
        val noteState: LiveData<NoteState>
        val noteEditState: LiveData<NoteEditState>
        val noteChartState: LiveData<NoteChartState>

        fun getAll()
        fun getById(id: Long)
        fun archive(id: Long, archived: Int)
        fun insertNote(note: Note)
        fun deleteNote(id: Long)
        fun updateNote(note: Note)
        fun getFilteredData(list: List<String>)
        fun clearUp()
        fun getLastDaysNoteNum()
    }

}