package rs.raf.projekat2.ognjen_prica_10620.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.ognjen_prica_10620.R
import rs.raf.projekat2.ognjen_prica_10620.data.models.Note
import rs.raf.projekat2.ognjen_prica_10620.databinding.FragmentEditNoteBinding
import rs.raf.projekat2.ognjen_prica_10620.presentation.contracts.MainContract
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.NoteEditState
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.NoteState
import rs.raf.projekat2.ognjen_prica_10620.presentation.viewmodel.NoteViewModel


class EditNoteFragment : Fragment(R.layout.fragment_edit_note) {
    private val noteViewModel: MainContract.NoteViewModel by sharedViewModel<NoteViewModel>()

    private var _binding: FragmentEditNoteBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
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
        initListeners()
    }

    private fun initListeners() {
        binding.btnEdit2.setOnClickListener {

            val title = binding.etAddTitle2.text.toString()
            val content = binding.etAddContent2.text.toString()

            arguments?.let { it1 ->
                Note(
                    id = arguments?.getLong("note"),
                    title = title,
                    content = content,
                    archived = it1.getBoolean("archived"),
                    dateCreated = it1.getInt("date")
                )
            }?.let { it2 ->
                noteViewModel.updateNote(
                    it2
                )
            }
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.remove(this)
            transaction.commit()
            requireActivity().supportFragmentManager.popBackStackImmediate()

        }
        binding.btnOdustani2.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.remove(this)
            transaction.commit()
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }
    }

    private fun initObservers() {
        noteViewModel.noteEditState.observe(viewLifecycleOwner) {
            //// Timber.e(it.toString())
            renderState(it)
        }
        arguments?.getLong("note")?.let { noteViewModel.getById(it) }
    }

    private fun renderState(state: NoteEditState) {
        when (state) {
            is NoteEditState.Success -> {
                binding.etAddTitle2.setText(state.note.title)
                binding.etAddContent2.setText(state.note.content)
            }
            is NoteEditState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is NoteEditState.DataFetched -> {
                Toast.makeText(
                    context,
                    "[NOTES] Fresh data fetched from the server",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            is NoteEditState.Loading -> {
            }
        }
    }

}