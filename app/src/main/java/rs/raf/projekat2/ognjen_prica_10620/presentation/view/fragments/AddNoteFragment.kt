package rs.raf.projekat2.ognjen_prica_10620.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.ognjen_prica_10620.R
import rs.raf.projekat2.ognjen_prica_10620.data.models.Note
import rs.raf.projekat2.ognjen_prica_10620.databinding.FragmentAddNoteBinding
import rs.raf.projekat2.ognjen_prica_10620.presentation.contracts.MainContract
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.activities.MainActivity
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.adapter.NoteAdapter
import rs.raf.projekat2.ognjen_prica_10620.presentation.viewmodel.NoteViewModel


class AddNoteFragment : Fragment(R.layout.fragment_add_note) {
    private val noteViewModel: MainContract.NoteViewModel by sharedViewModel<NoteViewModel>()

    private var _binding: FragmentAddNoteBinding? = null

    private val binding get() = _binding!!

    private val filterList: MutableList<String> = mutableListOf("", "")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
//        noteViewModel.clearUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        // initObservers()
    }

    private fun initUi() {
        initListeners()
    }

    private fun initListeners() {
        binding.btnDodaj.setOnClickListener {

            val title = binding.etAddTitle.text.toString()
            val content = binding.etAddContent.text.toString()

            noteViewModel.insertNote(
                Note(
                    title = title,
                    content = content,
                    archived = false,
                    dateCreated = (Math.random() * 10).toInt()
                )
            )

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.remove(this)
            transaction.commit()
            requireActivity().supportFragmentManager.popBackStackImmediate()

        }
        binding.btnOdustani.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.remove(this)
            transaction.commit()
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }
    }
}