package edu.ucne.abrahamelhage_ap2_p1.di

import android.content.Context
import androidx.room.Room
import com.example.rickmorty.data.local.remote.Api.CharacterApi
import com.example.rickmorty.data.local.remote.Api.EpisodeApi
import com.example.rickmorty.data.local.remote.Api.LocationApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()


    private const val BASE_URL = "https://rickandmortyapi.com/"


    @Provides
    @Singleton
    fun characterApi(moshi: Moshi): CharacterApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(CharacterApi::class.java)
    }

    @Provides
    @Singleton
    fun locationApi(moshi: Moshi): LocationApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(LocationApi::class.java)
    }



    @Provides
    @Singleton
    fun episodeApi(moshi: Moshi): EpisodeApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(EpisodeApi::class.java)
    }
}
