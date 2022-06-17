package rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2.ognjen_prica_10620.data.models.Lecture
import rs.raf.projekat2.ognjen_prica_10620.databinding.LectureListItemBinding
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.diff.LectureDiffCallback
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.viewholder.LectureViewHolder

class LectureAdapter : ListAdapter<Lecture, LectureViewHolder>(LectureDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureViewHolder {
        val itemBinding =
            LectureListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LectureViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LectureViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}