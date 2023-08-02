package com.trungdz.nfcproject.presentation.di

import com.trungdz.nfcproject.data.api.AppDiemDanhApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

//        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).client(client)
//            .baseUrl("http://192.168.111.190:8080/").build()
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).client(client)
            .baseUrl("http://10.0.2.2:8080/").build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): AppDiemDanhApiService {
        return retrofit.create(AppDiemDanhApiService::class.java)
    }
}