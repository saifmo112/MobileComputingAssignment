package com.saif.mobilecomputingassignment.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FlashcardDao {
    @Insert
    suspend fun insert(flashcard: Flashcard)

    @Query("SELECT DISTINCT category FROM flashcards ORDER BY category")
    suspend fun getAllCategories(): List<String>

    @Query("SELECT * FROM flashcards WHERE category = :category ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomFlashcardByCategory(category: String): Flashcard?

    @Query("SELECT COUNT(*) FROM flashcards WHERE category = :category")
    suspend fun getFlashcardCountByCategory(category: String): Int
}