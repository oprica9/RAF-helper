package rs.raf.projekat2.ognjen_prica_10620.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.ognjen_prica_10620.data.datasources.local.UserDatabase
import rs.raf.projekat2.ognjen_prica_10620.data.repositories.UserRepository
import rs.raf.projekat2.ognjen_prica_10620.data.repositories.UserRepositoryImpl
import rs.raf.projekat2.ognjen_prica_10620.presentation.viewmodel.UserViewModel

val user_module = module {

    viewModel { UserViewModel(userRepository = get()) }

    single<UserRepository> { UserRepositoryImpl(userDao = get()) }

    single { get<UserDatabase>().getUserDao() }

}