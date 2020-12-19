package com.akerimtay.movieapp.di

import com.akerimtay.movieapp.BuildConfig
import com.akerimtay.movieapp.data.remote.MovieApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val connectTimeOut = 10L
        val readTimeOut = 10L
        val writeTimeOut = 10L
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(connectTimeOut, TimeUnit.SECONDS)
        builder.readTimeout(readTimeOut, TimeUnit.SECONDS)
        builder.writeTimeout(writeTimeOut, TimeUnit.SECONDS)
        builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.REST_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
}

val apiModule = module {
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    single { provideMovieApi(get()) }
}