package rs.raf.projekat2.ognjen_prica_10620.presentation.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat2.ognjen_prica_10620.data.models.Note
import rs.raf.projekat2.ognjen_prica_10620.data.repositories.NoteRepository
import rs.raf.projekat2.ognjen_prica_10620.presentation.contracts.MainContract
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.NoteChartState
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.NoteEditState
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.NoteState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class NoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel(),
    MainContract.NoteViewModel {

    private val subscriptions = CompositeDisposable()
    override val item: MutableState<List<NoteChartState>> = mutableStateOf(listOf())
    override val test: List<NoteChartState> = listOf()

    override val noteState: MutableLiveData<NoteState> = MutableLiveData()
    override val noteEditState: MutableLiveData<NoteEditState> = MutableLiveData()
    override val noteChartState: MutableLiveData<NoteChartState> = MutableLiveData()

    private val publishSubject: PublishSubject<List<String>> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                noteRepository
                    .getFilteredData(it[0], it[1].toInt())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        //// Timber.e("Error in publish subject")
                    }
            }
            .subscribe(
                {
                    // Timber.e("Notes: $it")
                    noteState.value = NoteState.Success(it)
                }, {
                    noteState.value =
                        NoteState.Error("Error happened while geting data notes")
                    //// Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAll() {
        val subscription = noteRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    noteState.value = NoteState.Success(it)
                },
                {
                    noteState.value =
                        NoteState.Error("Error happened while fetching data from db NOTES")
                    //// Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getById(id: Long) {
        val subscription = noteRepository
            .getById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    noteEditState.value = NoteEditState.Success(it)
                },
                {
                    noteEditState.value =
                        NoteEditState.Error("Error happened while fetching data from db NOTES EDIT")
                    //// Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun archive(id: Long, archived: Int) {
        val subscription = noteRepository
            .archive(id, archived)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    // Timber.e("UPDATE ARCHIVED")
                },
                {
                    // Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun insertNote(note: Note) {
        // Timber.e("INSERT NOTE 1")
        val subscription = noteRepository
            .insertNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    // Timber.e("INESRT NOTE COMPLETE")
                },
                {
                    // Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun deleteNote(id: Long) {
        val subscription = noteRepository
            .deleteNote(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    // Timber.e("DELETE NOTE COMPLETE")
                },
                {
                    // Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun updateNote(note: Note) {
        val subscription = noteRepository
            .updateNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    // Timber.e("UPDATE NOTE COMPLETE")
                },
                {
                    // Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getFilteredData(list: List<String>) {
        publishSubject.onNext(arrayListOf(list[0], list[1]))
    }

    override fun clearUp() {
        TODO("Not yet implemented")
    }

    override fun getLastDaysNoteNum() {

        val subscription = noteRepository
            .getLastDaysNoteNum()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val map: HashMap<Int, Int> = HashMap()
                    for (note in it) {
                        map[note.dateCreated] = map[note.dateCreated]?.plus(1) ?: 1
                    }
                    var maxValue: Int = -1;
                    var maxKey: Int = -1;
                    val retMap: HashMap<Int, Float> = HashMap()
                    map.forEach { (key, value) ->
                        if (value > maxValue) {
                            maxValue = value
                            maxKey = key

                        }
                    }
                    // Timber.e("MAX VALUE = $maxValue")
                    map.forEach { (key, value) ->

                        // // Timber.e("$key $value")
                        retMap[key] = norm(value, 0, maxValue)
                        // // Timber.e("$key " + retMap[key])
                    }

                    noteChartState.value = NoteChartState.Success(retMap)
                    item.value = listOf(NoteChartState.Success(retMap))
                },
                {
                    noteChartState.value =
                        NoteChartState.Error("Error happened while fetching data from db NOTES EDIT")
                    //// Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

    private fun norm(x: Int, a: Int, b: Int): Float {

        return (x.toFloat() - a.toFloat()) / (b.toFloat() - a.toFloat())
    }

}