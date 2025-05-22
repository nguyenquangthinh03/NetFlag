package project.dheeraj.netflag.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import project.dheeraj.netflag.data.api.NetworkService
import project.dheeraj.netflag.data.model.Movie
import project.dheeraj.netflag.utils.CONSTANTS
import retrofit2.HttpException
import java.io.IOException



private const val DEFAULT_PAGE = 1

class SearchPagingSource(
        private val networkApi : NetworkService,
        private val query : String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val position = params.key ?: DEFAULT_PAGE

        return try {

            val response = networkApi.searchMovie(query, position, CONSTANTS.API_KEY)
            val data = response.body()!!.results

            LoadResult.Page(
                    data = data,
                    prevKey = if(position == DEFAULT_PAGE) null else position-1,
                    nextKey = if(data.isEmpty()) null else position+1
            )

        }
        catch(exception : IOException) {
            LoadResult.Error(exception)
        }
        catch(exception : HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int {
        return 1
    }

}