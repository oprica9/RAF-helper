package rs.raf.projekat2.ognjen_prica_10620.presentation.view.recycler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Consumer;

import rs.raf.projekat2.ognjen_prica_10620.R;
import rs.raf.projekat2.ognjen_prica_10620.data.models.Note;

public class NoteAdapterJ extends ListAdapter<Note, NoteAdapterJ.ViewHolder> {

    private final Consumer<Note> deleteNote;
    private final Consumer<Note> editNote;
    private final Consumer<Note> archiveNote;

    public NoteAdapterJ(@NonNull DiffUtil.ItemCallback<Note> diffCallback, Consumer<Note> deleteNote, Consumer<Note> editNote, Consumer<Note> archiveNote) {
        super(diffCallback);
        this.deleteNote = deleteNote;
        this.editNote = editNote;
        this.archiveNote = archiveNote;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent, false);
        return new ViewHolder(
                view,
                position -> {
                    Note note = getItem(position);
                    deleteNote.accept(note);
                },
                position -> {
                    Note note = getItem(position);
                    editNote.accept(note);
                },
                position -> {
                    Note note = getItem(position);
                    archiveNote.accept(note);
                });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note Note = getItem(position);
        holder.bind(Note);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView, Consumer<Integer> deleteItem, Consumer<Integer> editItem, Consumer<Integer> archiveItem) {
            super(itemView);
            itemView.findViewById(R.id.btnDelete).setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION)
                    deleteItem.accept(getBindingAdapterPosition());
            });

            itemView.findViewById(R.id.btnEdit).setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION)
                    editItem.accept(getBindingAdapterPosition());
            });

            itemView.findViewById(R.id.btnArchive).setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION)
                    archiveItem.accept(getBindingAdapterPosition());
            });
        }

        public void bind(Note Note) {
            ((TextView) itemView.findViewById(R.id.tvNoteTitle)).setText(Note.getTitle());
            ((TextView) itemView.findViewById(R.id.tvNoteContent)).setText(Note.getContent());
        }

    }

}