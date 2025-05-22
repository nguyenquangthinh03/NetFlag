package project.dheeraj.netflag.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import project.dheeraj.netflag.utils.CONSTANTS

@Entity(tableName = CONSTANTS.TABLE_NAME)
data class MovieDB (
    @PrimaryKey
    val id: Int,
    val userID: String,
    val poster_path: String?,
    val overview: String,
    val title: String,
    val backdrop_path: String
)