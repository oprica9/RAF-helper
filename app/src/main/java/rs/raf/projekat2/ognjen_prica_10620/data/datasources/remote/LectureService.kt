package rs.raf.projekat2.ognjen_prica_10620.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import rs.raf.projekat2.ognjen_prica_10620.data.models.LectureResponse

interface LectureService {

    @GET("raspored/json.php")
    fun getAll(): Observable<List<LectureResponse>>

}