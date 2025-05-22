package project.dheeraj.netflag.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import project.dheeraj.netflag.data.api.NetworkService
import project.dheeraj.netflag.utils.CONSTANTS
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    operator fun invoke() : NetworkService {
        return Retrofit.Builder()
                .baseUrl(CONSTANTS.BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkService::class.java)
    }


}