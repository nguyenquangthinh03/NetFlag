package project.dheeraj.netflag.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Cast (
    val id: Int = 0,
    val name: String = "Test",
    val original_name: String = "Test",
    val adult: Boolean = false,
    val character: String = "Test",
    val also_known_as: ArrayList<String>? = arrayListOf("Test"),  // gán cố định 1 phần tử "Test",
    val profile_path: String? = "Test",
    val gender: Int = 0,
    val known_for_department: String = "Test",
    val popularity: Number = 0,
    val credit_id: String = "Test",
    val department: String = "Test",
    val release_date: String = "Test",
    val job: String = "Test",
    val vote_count: Int = 0,
    val video: Boolean = false,
    val vote_average: Number = 0,
    val title: String = "Test",
    val genre_ids: List<Int> = listOf(0),
    val original_language: String = "Test",
    val original_title: String = "Test",
    val backdrop_path: String = "Test",
    val overview: String = "Test",
    val poster_path: String = "Test",
) : Parcelable