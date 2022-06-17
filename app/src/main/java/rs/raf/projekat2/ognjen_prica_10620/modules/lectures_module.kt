package rs.raf.projekat2.ognjen_prica_10620.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.ognjen_prica_10620.data.datasources.local.LectureDatabase
import rs.raf.projekat2.ognjen_prica_10620.data.datasources.remote.LectureService
import rs.raf.projekat2.ognjen_prica_10620.data.repositories.LectureRepository
import rs.raf.projekat2.ognjen_prica_10620.data.repositories.LectureRepositoryImpl
import rs.raf.projekat2.ognjen_prica_10620.presentation.viewmodel.LectureViewModel

val lectures_module = module {

    viewModel { LectureViewModel(lectureRepository = get()) }

    single<LectureRepository> {
        LectureRepositoryImpl(
            localDataSourceLecture = get(),
            remoteDataSourceLecture = get()
        )
    }

    single { get<LectureDatabase>().getLectureDao() }

    single<LectureService> { create(retrofit = get()) }

}