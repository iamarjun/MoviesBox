package com.arjun.moviesbox.di

import com.arjun.moviesbox.BuildConfig
import com.arjun.moviesbox.TmdbAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @Provides
    fun provideHttpClientBuilder(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addNetworkInterceptor {
            val url: HttpUrl = it.request().url().newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY).build()
            it.proceed(it.request().newBuilder().url(url).build())
        }
        .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.TMDB_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    fun provideNetworkService(retrofit: Retrofit): TmdbAPI = retrofit.create(TmdbAPI::class.java)
}