package project.dheeraj.netflag.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import project.dheeraj.netflag.data.local.dao.BookmarkDao
import project.dheeraj.netflag.data.model.MovieDB
import project.dheeraj.netflag.data.repository.NetworkRepository
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ViewAllViewModel @Inject constructor(
    private val databaseDao : BookmarkDao,
    private val repository : NetworkRepository,


    ) : ViewModel() {

    private val _bookmarks = MutableLiveData<List<MovieDB>>()
    val uid:String = FirebaseAuth.getInstance().currentUser!!.uid
    var bookmarks : MutableLiveData<List<MovieDB>> = _bookmarks

    fun fetchPopular() =
        repository.getPopularMovieResult().cachedIn(viewModelScope)

    fun fetchUpcoming() =
        repository.getUpcomingMovieResult().cachedIn(viewModelScope)

    fun fetchTopRated() =
        repository.getTopRatedMovieResult().cachedIn(viewModelScope)

    fun fetchBookmarks() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = databaseDao.getAllBookmark(uid)
            bookmarks.postValue(data)
        }
    }

}