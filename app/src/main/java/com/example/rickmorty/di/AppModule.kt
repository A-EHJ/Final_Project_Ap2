package edu.ucne.abrahamelhage_ap2_p1.di

import android.content.Context
import androidx.room.Room
import com.example.rickmorty.data.Network.remote.Api.CharacterApi
import com.example.rickmorty.data.Network.remote.Api.CharacterEpisodeApi
import com.example.rickmorty.data.Network.remote.Api.EpisodeApi
import com.example.rickmorty.data.Network.remote.Api.LocationApi
import com.example.rickmorty.data.Network.remote.Api.LocationResidentApi
import com.example.rickmorty.data.local.dao.CharacterDao
import com.example.rickmorty.data.local.dao.CharacterEpisodeDao
import com.example.rickmorty.data.local.dao.EpisodeDao
import com.example.rickmorty.data.local.dao.LocationDao
import com.example.rickmorty.data.local.dao.LocationResidentDao
import com.example.rickmorty.data.local.database.RMDb
import com.example.rickmorty.data.repository.Local.CharacterEpisodeLocalRepository
import com.example.rickmorty.data.repository.Local.CharacterLocalRepository
import com.example.rickmorty.data.repository.Local.EpisodeLocalRepository
import com.example.rickmorty.data.repository.Local.LocationLocalRepository
import com.example.rickmorty.data.repository.Local.LocationResidentLocalRepository
import com.example.rickmorty.data.repository.Net.CharacterEpisodeNetRepository
import com.example.rickmorty.data.repository.Net.CharacterNetRepository
import com.example.rickmorty.data.repository.Net.EpisodeNetRepository
import com.example.rickmorty.data.repository.Net.LocationNetRepository
import com.example.rickmorty.data.repository.Net.LocationResidentNetRepository
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

    @Singleton
    @Provides
    fun provideCharacterLocalRepository(characterDao: CharacterDao): CharacterLocalRepository {
        return CharacterLocalRepository(characterDao)
    }

    @Singleton
    @Provides
    fun provideEpisodeLocalRepository(episodeDao: EpisodeDao): EpisodeLocalRepository {
        return EpisodeLocalRepository(episodeDao)
    }

    @Singleton
    @Provides
    fun provideLocationLocalRepository(locationDao: LocationDao): LocationLocalRepository {
        return LocationLocalRepository(locationDao)
    }

    @Singleton
    @Provides
    fun provideCharacterEpisodeLocalRepository(characterEpisodeDao: CharacterEpisodeDao): CharacterEpisodeLocalRepository {
        return CharacterEpisodeLocalRepository(characterEpisodeDao)
    }

    @Singleton
    @Provides
    fun provideLocationResidentLocalRepository(locationResidentDao: LocationResidentDao): LocationResidentLocalRepository {
        return LocationResidentLocalRepository(locationResidentDao)
    }

    @Provides
    @Singleton
    fun provideCharacterNetRepository(
        characterApi: CharacterApi
    ): CharacterNetRepository {
        return CharacterNetRepository(characterApi)
    }

    @Provides
    @Singleton
    fun provideCharacterEpisodeNetRepository(
        characterEpisodeApi: CharacterEpisodeApi
    ): CharacterEpisodeNetRepository {
        return CharacterEpisodeNetRepository(characterEpisodeApi)
    }

    @Provides
    @Singleton
    fun provideLocationResidentNetRepository(
        locationResidentApi: LocationResidentApi
    ): LocationResidentNetRepository {
        return LocationResidentNetRepository(locationResidentApi)
    }

    @Provides
    @Singleton
    fun provideLocaionNetRepository(
        locationApi: LocationApi
    ): LocationNetRepository {
        return LocationNetRepository(locationApi)
    }

    @Provides
    @Singleton
    fun provideEpisodeNetRepository(
        episodeApi: EpisodeApi
    ): EpisodeNetRepository {
        return EpisodeNetRepository(episodeApi)
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
