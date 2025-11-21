package com.saif.mobilecomputingassignment
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "flashcards")
data class Flashcard(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Auto-generated Primary Key
    val question: String,
    val answer: String,
    val category: String
)


@Dao
interface FlashcardDao {

    @Insert
    suspend fun insert(flashcard: Flashcard)


    @Query("SELECT DISTINCT category FROM flashcards ORDER BY category ASC")
    fun getAllCategories(): Flow<List<String>>


    @Query("SELECT * FROM flashcards WHERE category = :category")
    suspend fun getFlashcardsByCategory(category: String): List<Flashcard>
}