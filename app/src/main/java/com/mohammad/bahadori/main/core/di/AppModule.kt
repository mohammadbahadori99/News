package com.mohammad.bahadori.main.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.api.ApiUrlHelper
import com.example.core.api.DefaultIfNullFactory
import com.example.core.api.HeaderInterceptor
import com.example.core.api.TLSSocketFactory
import com.example.core.pref.PreferencesDataStore
import com.mohammad.bahadori.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.example.data_article.db.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(
        @ApplicationContext context: Context
    ): PreferencesDataStore = PreferencesDataStore(context)

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(DefaultIfNullFactory())
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Singleton
    @Provides
    fun provideHeaderInterceptor(): HeaderInterceptor = HeaderInterceptor()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient = okHttpClientBuilder(httpLoggingInterceptor, headerInterceptor).build()

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, httpClient: OkHttpClient): Retrofit {
        return retrofitBuilder(moshi)
            .client(httpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): com.example.data_article.db.AppDb {
        return Room
            .databaseBuilder(context, com.example.data_article.db.AppDb::class.java, "com.mohammad.bahadori.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    private fun okHttpClientBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .dispatcher(Dispatcher(Executors.newFixedThreadPool(20)).apply {
                maxRequests = 20
                maxRequestsPerHost = 20
            })
            .connectionPool(ConnectionPool(100, 30L, TimeUnit.SECONDS))
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(headerInterceptor)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .sslSocketFactory(
                TLSSocketFactory(),
                TLSSocketFactory().trustManager
            )
    }

    private fun retrofitBuilder(moshi: Moshi): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(ApiUrlHelper.API_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }
}
