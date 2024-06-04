package ua.acclorite.book_story.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import ua.acclorite.book_story.data.remote.dto.LatestReleaseInfo
import ua.acclorite.book_story.domain.model.Book
import ua.acclorite.book_story.domain.model.History
import ua.acclorite.book_story.domain.model.NullableBook
import ua.acclorite.book_story.domain.util.CoverImage
import ua.acclorite.book_story.domain.util.LanguageCode
import ua.acclorite.book_story.domain.util.Resource
import ua.acclorite.book_story.presentation.data.MainState
import java.io.File

interface BookRepository {

    suspend fun getBooks(
        query: String
    ): List<Book>

    suspend fun getBooksById(
        ids: List<Int>
    ): List<Book>

    suspend fun getBookText(
        textPath: String
    ): List<String>

    suspend fun insertBook(
        book: Book,
        coverImage: CoverImage?,
        text: List<String>
    ): Boolean

    suspend fun updateBooks(
        books: List<Book>
    )

    suspend fun updateBookWithText(
        book: Book,
        text: List<String>
    ): Boolean

    suspend fun updateCoverImageOfBook(
        bookWithOldCover: Book,
        newCoverImage: CoverImage?
    )

    suspend fun deleteBooks(
        books: List<Book>
    )


    suspend fun <T> retrieveDataFromDataStore(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T>

    suspend fun <T> putDataToDataStore(
        key: Preferences.Key<T>,
        value: T
    )


    suspend fun getAllSettings(scope: CoroutineScope): MainState

    suspend fun getFilesFromDevice(query: String = ""): Flow<Resource<List<File>>>

    suspend fun getBooksFromFiles(files: List<File>): List<NullableBook>


    suspend fun insertHistory(history: List<History>)

    suspend fun getHistory(): Flow<Resource<List<History>>>

    suspend fun getLatestBookHistory(bookId: Int): History?

    suspend fun deleteWholeHistory()

    suspend fun deleteBookHistory(bookId: Int)

    suspend fun deleteHistory(
        history: List<History>
    )


    suspend fun checkForUpdates(postNotification: Boolean): LatestReleaseInfo?


    suspend fun isLanguageModelDownloaded(languageCode: String): Boolean

    suspend fun downloadLanguageModel(
        languageCode: String,
        onCompleted: () -> Unit,
        onFailure: (Exception) -> Unit
    )

    suspend fun deleteLanguageModel(languageCode: String): Boolean

    suspend fun identifyLanguage(text: String): Resource<String>

    suspend fun translateText(
        sourceLanguage: LanguageCode,
        targetLanguage: LanguageCode,
        text: String
    ): Resource<String>
}