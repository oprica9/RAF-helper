package rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.ognjen_prica_10620.data.models.Note

class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.archived == newItem.archived &&
                oldItem.content == newItem.content &&
                oldItem.title == newItem.title &&
                oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.archived == newItem.archived &&
                oldItem.content == newItem.content &&
                oldItem.title == newItem.title &&
                oldItem.id == newItem.id
    }

}