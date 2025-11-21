package com.saif.mobilecomputingassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.saif.mobilecomputingassignment.databinding.ActivityMainBinding
import com.saif.mobilecomputingassignment.viewmodel.FlashcardViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FlashcardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[FlashcardViewModel::class.java]

        binding.btnAddFlashcard.setOnClickListener {
            addFlashcard()
        }

        binding.btnGoToQuiz.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }
    }

    private fun addFlashcard() {
        val question = binding.etQuestion.text.toString().trim()
        val answer = binding.etAnswer.text.toString().trim()
        val category = binding.etCategory.text.toString().trim()

        if (question.isEmpty() || answer.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.insertFlashcard(question, answer, category) {
            runOnUiThread {
                Toast.makeText(this, "Flashcard added successfully!", Toast.LENGTH_SHORT).show()
                binding.etQuestion.text?.clear()
                binding.etAnswer.text?.clear()
                binding.etCategory.text?.clear()
            }
        }
    }
}