package rs.raf.projekat2.ognjen_prica_10620.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LectureResponse(
    val predmet: String,
    val tip: String,
    val nastavnik: String,
    val grupe: String,
    val dan: String,
    val termin: String,
    val ucionica: String
)