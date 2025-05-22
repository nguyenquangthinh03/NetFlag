package project.dheeraj.netflag.data.api

import retrofit2.Response
import timber.log.Timber


abstract class SafeApiRequest {

    suspend fun <T: Any> apiRequest(call : suspend() -> Response<T>): T {

        Timber.e("Api Request")
        val response = call.invoke()

        if (response.isSuccessful && response.body() != null) {
            Timber.e(response.body().toString())
            return response.body()!!
        }
        else {
            Timber.e(response.code().toString())
            Timber.e(response.message())
            throw Exception(response.code().toString())
        }

    }

}