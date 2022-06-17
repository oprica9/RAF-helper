package rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2.ognjen_prica_10620.data.models.Note
import rs.raf.projekat2.ognjen_prica_10620.databinding.NoteListItemBinding
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.diff.NoteDiffCallback
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.viewholder.NoteViewHolder

class NoteAdapter() : ListAdapter<Note, NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemBinding =
            NoteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}