package com.saif.mobilecomputingassignment


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.Flashcard
import com.example.flashcards.data.FlashcardDatabase
import kotlinx.coroutines.launch

class FlashcardViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = FlashcardDatabase.getDatabase(application).flashcardDao()

    fun insertFlashcard(question: String, answer: String, category: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            dao.insert(Flashcard(question = question, answer = answer, category = category))
            onSuccess()
        }
    }

    fun getAllCategories(onResult: (List<String>) -> Unit) {
        viewModelScope.launch {
            val categories = dao.getAllCategories()
            onResult(categories)
        }
    }

    fun getRandomFlashcard(category: String, onResult: (Flashcard?) -> Unit) {
        viewModelScope.launch {
            val flashcard = dao.getRandomFlashcardByCategory(category)
            onResult(flashcard)
        }
    }
}