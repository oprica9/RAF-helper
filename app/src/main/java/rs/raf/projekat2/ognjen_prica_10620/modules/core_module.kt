package rs.raf.projekat2.ognjen_prica_10620.modules

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rs.raf.projekat2.ognjen_prica_10620.BuildConfig
import rs.raf.projekat2.ognjen_prica_10620.data.datasources.local.LectureDatabase
import rs.raf.projekat2.ognjen_prica_10620.data.datasources.local.UserDatabase
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit


val core_module = module {

    single<SharedPreferences> {
        androidApplication().getSharedPreferences(
            androidApplication().packageName,
            Context.MODE_PRIVATE
        )
    }

    single {
        Room.databaseBuilder(androidContext(), UserDatabase::class.java, "UserDb")
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    // Timber.e("ONCREATE DATABASE")
                    db.beginTransaction()

                    val user = ContentValues()
                    user.put("uid", 1)
                    user.put("username", "ogi")
                    user.put("pin", "123")
                    db.insert("users", SQLiteDatabase.CONFLICT_ABORT, user)
                    db.setTransactionSuccessful()
                    db.endTransaction()

                    db.beginTransaction()
                    val remember = ContentValues()
                    remember.put("userId", 1)
                    remember.put("rememberMe", 0)
                    db.insert("remember", SQLiteDatabase.CONFLICT_ABORT, remember)

                    db.setTransactionSuccessful()
                    db.endTransaction()
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    // do something every time database is open
                }
            })
            .build()
    }

    single {

        Room.databaseBuilder(androidContext(), LectureDatabase::class.java, "LectureDb")
            .fallbackToDestructiveMigration()
            .build()

    }



    single { createRetrofit(moshi = get(), httpClient = get()) }

    single { createMoshi() }

    single { createOkHttpClient() }

}

fun createMoshi(): Moshi {
    return Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()
}

fun createRetrofit(
    moshi: Moshi,
    httpClient: OkHttpClient
): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://rfidis.raf.edu.rs/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .client(httpClient)
        .build()
}

fun createOkHttpClient(): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.readTimeout(60, TimeUnit.SECONDS)
    httpClient.connectTimeout(60, TimeUnit.SECONDS)
    httpClient.writeTimeout(60, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
    }

    return httpClient.build()
}

// Metoda koja kreira servis
inline fun <reified T> create(retrofit: Retrofit): T {
    return retrofit.create<T>(T::class.java)
}