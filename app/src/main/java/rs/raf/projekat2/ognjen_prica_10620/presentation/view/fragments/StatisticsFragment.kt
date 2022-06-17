package rs.raf.projekat2.ognjen_prica_10620.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.ognjen_prica_10620.R
import rs.raf.projekat2.ognjen_prica_10620.presentation.composable.ChartScreen
import rs.raf.projekat2.ognjen_prica_10620.presentation.contracts.MainContract
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.NoteChartState
import rs.raf.projekat2.ognjen_prica_10620.presentation.viewmodel.NoteViewModel
import timber.log.Timber


class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private val noteViewModel: MainContract.NoteViewModel by sharedViewModel<NoteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = ComposeView(requireContext())
        view.apply {
            setContent {
                ChartScreen(noteViewModel)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initObservers()
    }

    private fun initObservers() {
        noteViewModel.noteChartState.observe(viewLifecycleOwner) {
            renderState(it)
        }
        //noteViewModel.getLastDaysNoteNum()
    }

    private fun renderState(state: NoteChartState) {
        when (state) {
            is NoteChartState.Success -> {
                // values in hashmap, put them in chart
                state.chartNotes
            }
            is NoteChartState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is NoteChartState.DataFetched -> {
                Toast.makeText(
                    context,
                    "[NOTES - chart] Fresh data fetched from the server",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
            is NoteChartState.Loading -> {
            }
        }
    }

    public fun drawChart(){

    }
}