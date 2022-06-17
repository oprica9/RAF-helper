package rs.raf.projekat2.ognjen_prica_10620.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat2.ognjen_prica_10620.data.models.Lecture
import rs.raf.projekat2.ognjen_prica_10620.data.models.Resource
import rs.raf.projekat2.ognjen_prica_10620.data.repositories.LectureRepository
import rs.raf.projekat2.ognjen_prica_10620.presentation.contracts.MainContract
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.AddLectureState
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.DaysState
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.GroupsState
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.LecturesState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class LectureViewModel(
    private val lectureRepository: LectureRepository
) : ViewModel(),
    MainContract.LectureViewModel {

    private val subscriptions = CompositeDisposable()

    override val lecturesState: MutableLiveData<LecturesState> = MutableLiveData()
    override val addDone: MutableLiveData<AddLectureState> = MutableLiveData()
    override val groups: MutableLiveData<GroupsState> = MutableLiveData()
    override val days: MutableLiveData<DaysState> = MutableLiveData()

    private val publishSubject: PublishSubject<List<String>> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                lectureRepository
                    .getFilteredData(it[0], it[1], it[2], it[3])
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        //// Timber.e("Error in publish subject")
                    }
            }
            .subscribe(
                {
                    // Timber.e("LECTURES: $it")
                    lecturesState.value = LecturesState.Success(it)
                }, {
                    lecturesState.value =
                        LecturesState.Error("Error happened while geting data prof")
                    //// Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllLectures() {
        val subscription = lectureRepository
            .fetchAll()
            .startWith(Resource.Loading()) // da krenemo nas subscription sa nekim podatkom
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        is Resource.Loading -> lecturesState.value = LecturesState.Loading
                        is Resource.Success -> lecturesState.value = LecturesState.DataFetched
                        is Resource.Error -> lecturesState.value =
                            LecturesState.Error("Error happened while fetching data from db")
                    }
                },
                {
                    lecturesState.value =
                        LecturesState.Error("Nema neta. Ili gledate kesirane podatke ili nisu nikad bili kesirani")
                }

            )
        subscriptions.add(subscription)
    }

    override fun getAllLectures() {
        val subscription = lectureRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    lecturesState.value = LecturesState.Success(it)
                },
                {
                    lecturesState.value =
                        LecturesState.Error("Greska")
                    //// Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllGroups() {
        val subscription = lectureRepository
            .getGroups()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { it ->
                    val list: MutableList<String> = mutableListOf()
                    list.add("Izaberi grupu")
                    it.forEach { it2 ->
                        val tempList = it2.split(",")
                        tempList.forEach { it3 ->
                            if (!list.contains(it3)) {
                                list.add(it3.trim())
                            }
                        }
                    }

                    groups.value = GroupsState.Success(list)
                },
                {
                    groups.value =
                        GroupsState.Error("Error happened while fetching groups from db")
                    //// Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllDays() {
        val subscription = lectureRepository
            .getDays()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { it ->
                    val list: MutableList<String> = mutableListOf()
                    list.add("Izaberi dan")
                    it.forEach { it2 ->
                        val tempList = it2.split(",")
                        tempList.forEach { it3 ->
                            if (!list.contains(it3)) {
                                list.add(it3.trim())
                            }
                        }
                    }

                    days.value = DaysState.Success(list)
                },
                {
                    groups.value =
                        GroupsState.Error("Error happened while fetching groups from db")
                    //// Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

    override fun getFilteredData(list: List<String>) {
        publishSubject.onNext(arrayListOf(list[0], list[1], list[2], list[3]))
    }


}