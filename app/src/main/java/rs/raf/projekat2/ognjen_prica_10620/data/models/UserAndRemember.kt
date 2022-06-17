package rs.raf.projekat2.ognjen_prica_10620.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndRemember(
    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "uid",
        entityColumn = "userId"
    )
    val rememberEntity: RememberEntity?
)