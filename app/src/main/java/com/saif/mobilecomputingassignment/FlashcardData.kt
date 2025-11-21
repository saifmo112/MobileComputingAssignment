package com.saif.mobilecomputingassignment
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * 1. Flashcard Entity (Table Definition)
 * Represents a single row in the 'flashcards' table.
 */
@Entity(tableName = "flashcards")
data class Flashcard(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Auto-generated Primary Key
    val question: String,
    val answer: String,
    val category: String
)

/**
 * 2. Flashcard DAO (Data Access Object)
 * Defines methods for interacting with the Flashcard data.
 */
@Dao
interface FlashcardDao {
    /** Inserts a new flashcard into the database. */
    @Insert
    suspend fun insert(flashcard: Flashcard)

    /** Fetches a list of all unique categories to populate the Spinner/Dropdown. */
    @Query("SELECT DISTINCT category FROM flashcards ORDER BY category ASC")
    fun getAllCategories(): Flow<List<String>>

    /** Fetches all flashcards belonging to a specific category. Used for random selection. */
    @Query("SELECT * FROM flashcards WHERE category = :category")
    suspend fun getFlashcardsByCategory(category: String): List<Flashcard>
}