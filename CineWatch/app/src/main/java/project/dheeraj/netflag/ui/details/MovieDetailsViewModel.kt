package project.dheeraj.netflag.ui.details

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import project.dheeraj.netflag.data.local.dao.BookmarkDao
import project.dheeraj.netflag.data.model.Movie
import project.dheeraj.netflag.data.model.MovieDB
import project.dheeraj.netflag.data.model.Resource
import project.dheeraj.netflag.data.model.VideoResponse
import project.dheeraj.netflag.data.repository.NetworkRepository
import timber.log.Timber
import java.net.SocketTimeoutException
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val databaseDao : BookmarkDao,
    private val networkRepository: NetworkRepository

) : ViewModel() {

    private val _name = MutableLiveData("Movie Name")
    private val _movie = MutableLiveData<Movie>()
    private val _videos = MutableLiveData<VideoResponse>()
    private val uid: String = FirebaseAuth.getInstance().currentUser!!.uid

    var bookmark = MutableLiveData(false)
    var movieName : MutableLiveData<String> = _name
    var movie : MutableLiveData<Movie> = _movie
    var videos : MutableLiveData<VideoResponse> = _videos

    fun getMovieDetails(movie_id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val apiResponse = networkRepository.getMovieDetails(movie_id)
            movie.postValue(apiResponse)
        }
    }

    fun loadCast(movie_id : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            val apiResponse = networkRepository.getMovieCredits(movie_id)
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun loadSimilar(movie_id : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            // Fetch data from remote
            val apiResponse = networkRepository.getSimilarMovies(movie_id)
            emit(Resource.success(apiResponse))
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun getVideos(movie_id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiResponse = networkRepository.getVideos(movie_id)
                videos.postValue(apiResponse)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

    }

    fun bookmarkMovie() {
        movie.value!!.apply {
            val movieDb = MovieDB(id, uid , poster_path!!, overview!!, title!!, backdrop_path!!)
            viewModelScope.launch(Dispatchers.IO) {
                if (bookmark.value == true) {
                    databaseDao.removeMovie(movieDb)
                }
                else {
                    databaseDao.insertMovie(movieDb)
                }
            }
            Timber.e("Bookmark")
        }
    }

    fun checkBookmarkExist() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = databaseDao.bookmarkExist(movie.value!!.id, uid)
            bookmark.postValue(response)
        }
    }

}