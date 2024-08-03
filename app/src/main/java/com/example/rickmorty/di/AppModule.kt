package edu.ucne.abrahamelhage_ap2_p1.di

import android.content.Context
import androidx.room.Room
import com.example.rickmorty.data.local.dao.CharacterDao
import com.example.rickmorty.data.local.dao.CharacterEpisodeDao
import com.example.rickmorty.data.local.dao.EpisodeDao
import com.example.rickmorty.data.local.dao.LocationDao
import com.example.rickmorty.data.local.dao.LocationResidentDao
import com.example.rickmorty.data.local.database.RMDb
import com.example.rickmorty.data.network.remote.Api.CharacterApi
import com.example.rickmorty.data.network.remote.Api.CharacterEpisodeApi
import com.example.rickmorty.data.network.remote.Api.EpisodeApi
import com.example.rickmorty.data.network.remote.Api.LocationApi
import com.example.rickmorty.data.network.remote.Api.LocationResidentApi
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

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): RMDb {
        return Room.databaseBuilder(
            appContext,
            RMDb::class.java,
            "RMDb.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideCharacterDao(db: RMDb): CharacterDao {
        return db.characterDao()
    }

    @Singleton
    @Provides
    fun provideEpisodeDao(db: RMDb): EpisodeDao {
        return db.episodeDao()
    }

    @Singleton
    @Provides
    fun provideLocationDao(db: RMDb): LocationDao {
        return db.locationDao()
    }

    @Singleton
    @Provides
    fun provideCharacterEpisodeDao(db: RMDb): CharacterEpisodeDao {
        return db.characterEpisodeDao()
    }

    @Singleton
    @Provides
    fun provideLocationResidentDao(db: RMDb): LocationResidentDao {
        return db.locationResidentDao()
    }


    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()


    private const val BASE_URL = "https://rickmorty.azurewebsites.net/"


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

    @Provides
    @Singleton
    fun characterEpisodeApi(moshi: Moshi): CharacterEpisodeApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(CharacterEpisodeApi::class.java)
    }

    @Provides
    @Singleton
    fun locationResidentApi(moshi: Moshi): LocationResidentApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(LocationResidentApi::class.java)
    }

}
