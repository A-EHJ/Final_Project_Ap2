# Rick and Morty App

This is a Rick and Morty application developed using Jetpack Compose for the UI and Hilt for dependency injection. The app interacts with the Rick and Morty API and TMDb to fetch data about the characters, episodes, and locations from the series.

## Features

- **Character Visualization**: List and detail views of characters from the Rick and Morty series.
- **Location Visualization**: Details of locations, including the characters residing in each.
- **Episode Visualization**: Information about episodes, including titles, air dates, and appearing characters.
- **Pagination**: Support for pagination in the character list.
- **Dropdown Menu**: Implementation of a dropdown menu for selecting filters.
- **Local Database**: Use of Room for local data storage.
- **Dependency Injection**: Utilization of Hilt for dependency management.
- **State Management**: Use of ViewModel and LiveData to handle UI states.

## Architecture

The application follows the MVVM (Model-View-ViewModel) pattern and Clean Architecture to maintain a clear separation of concerns and improve code maintainability and scalability.

### MVVM (Model-View-ViewModel)

- **Model**: Contains business logic and data definitions. Includes repositories and domain classes.
- **View**: The user interface, built using Jetpack Compose.
- **ViewModel**: Manages UI logic and states. Communicates with the Model to fetch necessary data and provides this data to the View.

### Clean Architecture

- **Data Layer**: Responsible for managing data, whether from a local database, a remote API, or any other data source. Includes DAOs, Repositories, and API services.
- **Domain Layer**: Contains business rules and use cases. It is independent of the data layer and the presentation layer.
- **Presentation Layer**: Manages presentation logic and user interactions. Includes ViewModels and Composables.

## Installation

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Configure your API keys for the Rick and Morty API and TMDb in the respective files.
4. Sync the project with Gradle.
5. Run the application on an emulator or physical device.

## Project Structure

- **data**: Contains data models, DAOs, and Repositories.
- **domain**: Contains use cases and domain models.
- **screen**: Contains the different screens of the application, organized by functionality.
  - **character**: Screens related to characters.
  - **location**: Screens related to locations.
  - **episode**: Screens related to episodes.
- **di**: Contains Hilt modules for dependency injection.

## Using Jetpack Compose

The application makes extensive use of Jetpack Compose to build the UI. Here is an example of how character cards are built:

```kotlin
@Composable
fun CharacterInfo(characterIIN: CharacterIIN, onClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray.copy(alpha = 0.3f))
            .border(2.dp, Color.White, RoundedCornerShape(8.dp))
            .clickable { onClick(characterIIN.id) }
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = characterIIN.image,
                contentDescription = "Character Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(
                text = characterIIN.name,
                color = Color.White,
                style = titleTextStyle,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}
```

## Dependency Injection with Hilt

The application uses Hilt for dependency injection, facilitating the management of dependencies throughout the application. Below is an example of a Hilt module:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRickAndMortyApi(): RickAndMortyApi {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickAndMortyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rick_and_morty_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(db: AppDatabase): CharacterDao {
        return db.characterDao()
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(api: RickAndMortyApi, dao: CharacterDao): CharacterRepository {
        return CharacterRepositoryImpl(api, dao)
    }
}
```

## Contributions

Contributions are welcome. Please open an issue or submit a pull request.

## License

This project is licensed under the All-Seeing God License.

---

