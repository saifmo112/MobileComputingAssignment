package com.saif.mobilecomputingassignment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.saif.mobilecomputingassignment.data.Flashcard
import com.saif.mobilecomputingassignment.databinding.ActivityQuizBinding
import com.saif.mobilecomputingassignment.viewmodel.FlashcardViewModel

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private lateinit var viewModel: FlashcardViewModel
    private var currentFlashcard: Flashcard? = null
    private var isAnswerVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[FlashcardViewModel::class.java]

        loadCategories()

        binding.btnShowAnswer.setOnClickListener {
            toggleAnswer()
        }

        binding.btnNextQuestion.setOnClickListener {
            loadRandomQuestion()
        }
    }

    private fun loadCategories() {
        viewModel.getAllCategories { categories ->
            runOnUiThread {
                if (categories.isEmpty()) {
                    Toast.makeText(this, "No categories found. Add flashcards first!", Toast.LENGTH_LONG).show()
                    binding.spinnerCategory.isEnabled = false
                    return@runOnUiThread
                }

                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerCategory.adapter = adapter

                binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        loadRandomQuestion()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }
    }

    private fun loadRandomQuestion() {
        val selectedCategory = binding.spinnerCategory.selectedItem?.toString()
        if (selectedCategory == null) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.getRandomFlashcard(selectedCategory) { flashcard ->
            runOnUiThread {
                currentFlashcard = flashcard
                if (flashcard != null) {
                    binding.tvQuestion.text = flashcard.question
                    binding.tvAnswer.text = flashcard.answer
                    binding.tvAnswer.visibility = View.GONE
                    binding.btnShowAnswer.text = "Show Answer"
                    isAnswerVisible = false
                    binding.questionCard.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this, "No flashcards in this category", Toast.LENGTH_SHORT).show()
                    binding.questionCard.visibility = View.GONE
                }
            }
        }
    }

    private fun toggleAnswer() {
        isAnswerVisible = !isAnswerVisible
        if (isAnswerVisible) {
            binding.tvAnswer.visibility = View.VISIBLE
            binding.btnShowAnswer.text = "Hide Answer"
        } else {
            binding.tvAnswer.visibility = View.GONE
            binding.btnShowAnswer.text = "Show Answer"
        }
    }
}