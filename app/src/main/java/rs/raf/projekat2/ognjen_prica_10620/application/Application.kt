package rs.raf.projekat2.ognjen_prica_10620.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import rs.raf.projekat2.ognjen_prica_10620.modules.core_module
import rs.raf.projekat2.ognjen_prica_10620.modules.lectures_module
import rs.raf.projekat2.ognjen_prica_10620.modules.notes_module
import rs.raf.projekat2.ognjen_prica_10620.modules.user_module
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        val modules = listOf(
            core_module,
            user_module,
            lectures_module,
            notes_module

        )
        org.koin.core.context.startKoin {
            androidLogger(org.koin.core.logger.Level.ERROR)
            // Use application context
            androidContext(this@MainApplication)
            // Use properties from assets/koin.properties
            androidFileProperties()
            // Use koin fragment factory for fragment instantiation
            fragmentFactory()
            // modules
            modules(modules)
        }
    }

}