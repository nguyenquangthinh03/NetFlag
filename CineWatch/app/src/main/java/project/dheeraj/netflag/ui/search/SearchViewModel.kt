package project.dheeraj.netflag.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import project.dheeraj.netflag.data.model.Resource
import project.dheeraj.netflag.data.repository.NetworkRepository
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {

    fun searchMovie(query: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val apiResponse =
                networkRepository.getSearchResult(query).cachedIn(viewModelScope)
            emit(Resource.success(apiResponse))
        }
        catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun getSearchMovie(query: String) =
        networkRepository.getSearchResult(query).cachedIn(viewModelScope)



}