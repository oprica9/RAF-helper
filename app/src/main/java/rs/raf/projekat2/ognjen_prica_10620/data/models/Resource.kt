package rs.raf.projekat2.ognjen_prica_10620.data.models

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading<out T>(val message: String = "") : Resource<T>()
    data class Error<out T>(val error: Throwable = Throwable(), val data: T? = null): Resource<T>()
}

// neki kotlinov nacin da se napravi neka struktura, da se podaci wrappuju u strukturu
// pokusavamo da opisemo neku vrstu hijerarhije