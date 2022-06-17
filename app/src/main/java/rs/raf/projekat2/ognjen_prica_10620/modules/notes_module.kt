package rs.raf.projekat2.ognjen_prica_10620.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.ognjen_prica_10620.data.datasources.local.LectureDatabase
import rs.raf.projekat2.ognjen_prica_10620.data.datasources.local.UserDatabase
import rs.raf.projekat2.ognjen_prica_10620.data.datasources.remote.LectureService
import rs.raf.projekat2.ognjen_prica_10620.data.repositories.*
import rs.raf.projekat2.ognjen_prica_10620.presentation.viewmodel.LectureViewModel
import rs.raf.projekat2.ognjen_prica_10620.presentation.viewmodel.NoteViewModel

val notes_module = module {

    viewModel { NoteViewModel(noteRepository = get()) }

    single<NoteRepository> {
        NoteRepositoryImpl(
            noteDao = get()
        )
    }

    single { get<UserDatabase>().getNoteDao() }

}