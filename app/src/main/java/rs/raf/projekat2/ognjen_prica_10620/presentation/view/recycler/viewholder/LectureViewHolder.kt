package rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import rs.raf.projekat2.ognjen_prica_10620.data.models.Lecture
import rs.raf.projekat2.ognjen_prica_10620.databinding.LectureListItemBinding

class LectureViewHolder(private val itemBinding: LectureListItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(lecture: Lecture) {
        itemBinding.tvPredmet.text = lecture.subject
        itemBinding.tvDan.text = lecture.day
        itemBinding.tvGrupe.text = lecture.groups
        itemBinding.tvProfesor.text = lecture.prof
        itemBinding.tvTip.text = lecture.type
        itemBinding.tvVreme.text = lecture.time
        itemBinding.tvUcionica.text = lecture.room

    }

}