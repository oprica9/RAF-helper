package rs.raf.projekat2.ognjen_prica_10620.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.ognjen_prica_10620.R
import rs.raf.projekat2.ognjen_prica_10620.databinding.FragmentScheduleBinding
import rs.raf.projekat2.ognjen_prica_10620.presentation.contracts.MainContract
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.adapter.LectureAdapter
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.DaysState
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.GroupsState
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.LecturesState
import rs.raf.projekat2.ognjen_prica_10620.presentation.viewmodel.LectureViewModel
import timber.log.Timber


class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    private val lectureViewModel: MainContract.LectureViewModel by sharedViewModel<LectureViewModel>()

    private var _binding: FragmentScheduleBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: LectureAdapter

    private val filterList: MutableList<String> = mutableListOf("", "", "", "")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {
        initRecycler()
        initListeners()
    }

    private fun initSpinners1(arr1: List<String>) {
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            arr1
        )
        binding.spinner.adapter = arrayAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    filterList[GROUP] = p0?.getItemAtPosition(p2).toString()
                    lectureViewModel.getFilteredData(filterList)
                } else {
                    filterList[GROUP] = ""
                    lectureViewModel.getFilteredData(filterList)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }
        }

    }

    private fun initSpinners2(arr1: List<String>) {
        val arrayAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            arr1
        )
        binding.spinner2.adapter = arrayAdapter
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    filterList[DAY] = p0?.getItemAtPosition(p2).toString()
                    lectureViewModel.getFilteredData(filterList)
                } else {
                    filterList[DAY] = ""
                    lectureViewModel.getFilteredData(filterList)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }
        }

    }

    private fun initListeners() {
        binding.etProfPred.doAfterTextChanged {
            val filter = it.toString()
            filterList[PROF] = filter
            lectureViewModel.getFilteredData(filterList)
        }
    }

    private fun initRecycler() {
        binding.listRv.layoutManager = LinearLayoutManager(context)
        adapter = LectureAdapter()
        binding.listRv.adapter = adapter
    }

    private fun initObservers() {
        lectureViewModel.lecturesState.observe(viewLifecycleOwner, Observer {
            //// Timber.e(it.toString())
            renderState(it)
        })

        lectureViewModel.groups.observe(viewLifecycleOwner, Observer {
            //// Timber.e(it.toString())
            renderStateGroups(it)
        })

        lectureViewModel.days.observe(viewLifecycleOwner, Observer {
            //// Timber.e(it.toString())
            renderStateDays(it)
        })

        // Pravimo subscription kad observablu koji je vezan za getAll iz baze
        // Na svaku promenu tabele, obserrvable ce emitovati na onNext sve elemente
        // koji zadovoljavaju query
        lectureViewModel.getAllLectures()
        lectureViewModel.getAllGroups()
        lectureViewModel.getAllDays()
        // Pokrecemo operaciju dovlacenja podataka sa servera, kada podaci stignu,
        // bice sacuvani u bazi, tada ce se triggerovati observable na koji smo se pretplatili
        // preko metode getAllMovies()
        lectureViewModel.fetchAllLectures()
    }

    private fun renderStateDays(state: DaysState?) {
        when (state) {
            is DaysState.Success -> {
                initSpinners2(state.days)
            }
            is DaysState.Error -> {

            }
            else -> {
                //// Timber.e("GRESKA STATE GROUPS")
            }
        }
    }

    private fun renderStateGroups(state: GroupsState?) {
        when (state) {
            is GroupsState.Success -> {
                initSpinners1(state.groups)
            }
            is GroupsState.Error -> {

            }
            else -> {
                //// Timber.e("GRESKA STATE GROUPS")
            }
        }
    }

    private fun renderState(state: LecturesState) {
        when (state) {
            is LecturesState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.lectures)
            }
            is LecturesState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is LecturesState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG)
                    .show()
            }
            is LecturesState.Loading -> {
                // Timber.e("LOADING B")
                showLoadingState(true)
                // Timber.e("" + binding.loadingPb.isVisible)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.etProfPred.isVisible = !loading
        binding.listRv.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val GROUP = 0
        private const val DAY = 1
        private const val PROF = 2
        private const val SUBJ = 3
    }

}