package rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.viewholder

import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import rs.raf.projekat2.ognjen_prica_10620.R
import rs.raf.projekat2.ognjen_prica_10620.data.models.Note
import rs.raf.projekat2.ognjen_prica_10620.databinding.NoteListItemBinding
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.adapter.NoteAdapter

class NoteViewHolder(
    private val itemBinding: NoteListItemBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    private var btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    private var btnEdit: Button = itemView.findViewById(R.id.btnEdit)
    private var btnArchive: Button = itemView.findViewById(R.id.btnArchive)

    fun bind(note: Note) {
        itemBinding.tvNoteContent.text = note.content
        itemBinding.tvNoteTitle.text = note.title
    }

}