package project.dheeraj.netflag.data.local.dao

import androidx.room.*
import project.dheeraj.netflag.data.model.Movie
import project.dheeraj.netflag.data.model.MovieDB
import project.dheeraj.netflag.utils.CONSTANTS.Companion.TABLE_NAME


@Dao
interface BookmarkDao {

    /**
     * Insert [Movie] into the Movie Table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(Movie: MovieDB)

    /**
     * Delete movie from Database
     */
    @Delete
    suspend fun removeMovie(movie: MovieDB)

    /**
     * Delete [Movie] by [Movie.id]
     */
    @Query("Delete from ${TABLE_NAME} where id=:id")
    suspend fun deleteMovieById(id : Int)

    /**
     * Fetch all movies
     */
    @Query("Select * from $TABLE_NAME WHERE userID = :uid")
    fun getAllBookmark(uid:String) : List<MovieDB>

    /**
     * Check movie exist in DB
     */
    @Query("SELECT EXISTS (SELECT 1 FROM $TABLE_NAME WHERE id = :id AND userID = :uid)")
    fun bookmarkExist(id: Int, uid: String): Boolean


}