package rs.raf.projekat2.ognjen_prica_10620.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.ognjen_prica_10620.R
import rs.raf.projekat2.ognjen_prica_10620.data.models.Note
import rs.raf.projekat2.ognjen_prica_10620.databinding.FragmentNoteBinding
import rs.raf.projekat2.ognjen_prica_10620.presentation.contracts.MainContract
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.activities.MainActivity
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.adapter.NoteAdapter
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.adapter.NoteAdapterJ
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.diff.NoteDiffCallback
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.NoteState
import rs.raf.projekat2.ognjen_prica_10620.presentation.viewmodel.NoteViewModel
import timber.log.Timber

class NoteFragment : Fragment(R.layout.fragment_note) {

    private val noteViewModel: MainContract.NoteViewModel by sharedViewModel<NoteViewModel>()

    private var _binding: FragmentNoteBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: NoteAdapterJ
    private lateinit var adapter2: NoteAdapter

    private val filterList: MutableList<String> = mutableListOf("", "0")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
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

    private fun initListeners() {
        binding.etSearchTitleContent.doAfterTextChanged {
            val filter = it.toString()
            filterList[TITLE_CONTENT] = filter
            noteViewModel.getFilteredData(filterList)
        }
        binding.btnAddNote.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fcvMain, AddNoteFragment(), MainActivity.MAIN_FRAGMENT_TAG)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.switchArchive.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                filterList[1] = "" + 1
            } else {
                filterList[1] = "" + 0
            }
            noteViewModel.getFilteredData(filterList)
        }
    }

    private fun initRecycler() {
        binding.rvNotes.layoutManager = LinearLayoutManager(context)
        adapter = NoteAdapterJ(
            NoteDiffCallback(),
            {
                if (it != null) {
                    val id = it.id
                    if (id != null) {
                        noteViewModel.deleteNote(id)
                    }
                }
            },
            {
                val args = Bundle()
                if (it.id != null) {
                    args.putLong("note", it.id)
                }

                args.putBoolean("archived", it.archived)
                args.putInt("date", it.dateCreated)

                val nextFrag = EditNoteFragment()
                nextFrag.arguments = args

                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(
                    R.id.fcvMain,
                    nextFrag,
                    MainActivity.MAIN_FRAGMENT_TAG
                )
                transaction.addToBackStack(null)
                transaction.commit()
            },
            {
                if (it.id != null)
                    if (it.archived)
                        noteViewModel.archive(it.id, 0)
                    else
                        noteViewModel.archive(it.id, 1)

            }

        )
        adapter2 = NoteAdapter()
        binding.rvNotes.adapter = adapter
    }

    private fun initObservers() {
        noteViewModel.noteState.observe(viewLifecycleOwner) {
            //// Timber.e(it.toString())
            renderState(it)
        }
        noteViewModel.getFilteredData(filterList)
    }

    private fun renderState(state: NoteState) {
        when (state) {
            is NoteState.Success -> {
                adapter.submitList(state.notes)
            }
            is NoteState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is NoteState.DataFetched -> {
                Toast.makeText(
                    context,
                    "[NOTES] Fresh data fetched from the server",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
            is NoteState.Loading -> {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TITLE_CONTENT = 0
    }

}