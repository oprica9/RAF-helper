package rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.ognjen_prica_10620.data.models.Lecture

class LectureDiffCallback : DiffUtil.ItemCallback<Lecture>() {

    override fun areItemsTheSame(oldItem: Lecture, newItem: Lecture): Boolean {
        return oldItem.subject == newItem.subject &&
                oldItem.type == newItem.type &&
                oldItem.prof == newItem.prof &&
                oldItem.groups == newItem.groups &&
                oldItem.day == newItem.day &&
                oldItem.time == newItem.time &&
                oldItem.room == newItem.room

    }

    override fun areContentsTheSame(oldItem: Lecture, newItem: Lecture): Boolean {
        return oldItem.subject == newItem.subject &&
                oldItem.type == newItem.type &&
                oldItem.prof == newItem.prof &&
                oldItem.groups == newItem.groups &&
                oldItem.day == newItem.day &&
                oldItem.time == newItem.time &&
                oldItem.room == newItem.room
    }

}