package com.example.tpl0part2.Activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tpl0part2.Models.Film
import com.example.tpl0part2.databinding.ActivityFormBinding

/**
 * Activity for adding or editing a film in the application.
 * 
 * This activity provides a form interface for users to input film details including
 * title, description, genre, rating, release year, and watch priority. It supports
 * both adding new films and editing existing ones.
 * 
 * @property isEditMode Boolean flag indicating whether the activity is in edit mode
 * @property filmId Int? ID of the film being edited (null for new films)
 */
class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding
    private var isEditMode = false
    private var filmId: Int? = null

    /**
     * Called when the activity is starting. This is where most initialization should go.
     * 
     * @param savedInstanceState If the activity is being re-initialized after previously
     * being shut down then this Bundle contains the data it most recently supplied.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        handleIntent()
    }

    /**
     * Sets up the user interface components.
     * 
     * Initializes genre spinner, sets up click listeners, and configures the form.
     */
    private fun setupUI() {
        setupGenreSpinner()
        setupListeners()
    }

    /**
     * Sets up the genre spinner with predefined genres.
     * 
     * Populates the spinner with genre options from string resources.
     */
    private fun setupGenreSpinner() {
        val genres = listOf(
            getString(R.string.genre_action),
            getString(R.string.genre_comedy),
            getString(R.string.genre_drama),
            getString(R.string.genre_sci_fi),
            getString(R.string.genre_horror),
            getString(R.string.genre_romance)
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genres)
        binding.genreSpinner.setAdapter(adapter)
    }

    /**
     * Sets up click listeners for form buttons.
     * 
     * Configures save and cancel button actions.
     */
    private fun setupListeners() {
        binding.saveButton.setOnClickListener { saveFilm() }
        binding.cancelButton.setOnClickListener { finish() }
    }

    /**
     * Handles the intent data passed to this activity.
     * 
     * Checks if the activity was launched in edit mode and populates the form
     * with existing film data if applicable.
     */
    private fun handleIntent() {
        val film = intent.getSerializableExtra("film") as? Film
        if (film != null) {
            isEditMode = true
            filmId = film.id
            populateForm(film)
            binding.titleText.text = getString(R.string.edit_movie)
            binding.saveButton.text = getString(R.string.update)
        }
    }

    /**
     * Populates the form fields with existing film data.
     * 
     * @param film The film object containing data to populate the form
     */
    private fun populateForm(film: Film) {
        binding.titleEditText.setText(film.title)
        binding.descriptionEditText.setText(film.description)
        binding.genreSpinner.setText(film.genre, false)
        binding.ratingEditText.setText(film.rating.toString())
        binding.yearEditText.setText(film.year.toString())

        val priorityRadioId = when (film.watchPriority) {
            Film.Priority.HIGH -> R.id.priorityHigh
            Film.Priority.LOW -> R.id.priorityLow
            else -> R.id.priorityMedium
        }
        binding.priorityRadioGroup.check(priorityRadioId)
    }

    /**
     * Validates form inputs and saves the film data.
     * 
     * Performs validation on all form fields, shows appropriate error messages,
     * and saves the film if validation passes.
     */
    private fun saveFilm() {
        val title = binding.titleEditText.text.toString().trim()
        val description = binding.descriptionEditText.text.toString().trim()
        val genre = binding.genreSpinner.text.toString().trim()
        val ratingStr = binding.ratingEditText.text.toString().trim()
        val yearStr = binding.yearEditText.text.toString().trim()

        if (!validateForm(title, genre, ratingStr, yearStr)) {
            return
        }

        val rating = ratingStr.toFloat()
        val year = yearStr.toInt()
        val priority = getSelectedPriority()

        val film = Film(
            id = filmId ?: System.currentTimeMillis().toInt(),
            title = title,
            description = description,
            genre = genre,
            rating = rating,
            year = year,
            imageUrl = "", // In a real app, this would come from image selection
            watchPriority = priority
        )

        val resultIntent = intent.apply {
            putExtra("film", film)
            putExtra("isEdit", isEditMode)
        }
        setResult(RESULT_OK, resultIntent)
        finish()

        Toast.makeText(
            this,
            if (isEditMode) "Film updated successfully!" else "Film added successfully!",
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * Gets the selected watch priority from the radio group.
     * 
     * @return The selected [Film.Priority] enum value
     */
    private fun getSelectedPriority(): Film.Priority {
        return when (binding.priorityRadioGroup.checkedRadioButtonId) {
            R.id.priorityHigh -> Film.Priority.HIGH
            R.id.priorityLow -> Film.Priority.LOW
            else -> Film.Priority.MEDIUM
        }
    }

    /**
     * Validates all form fields.
     * 
     * @param title Film title
     * @param genre Film genre
     * @param ratingStr Film rating as string
     * @param yearStr Release year as string
     * @return Boolean indicating whether all fields are valid
     */
    private fun validateForm(title: String, genre: String, ratingStr: String, yearStr: String): Boolean {
        var isValid = true

        if (title.isEmpty()) {
            binding.titleEditText.error = getString(R.string.required_field)
            isValid = false
        } else {
            binding.titleEditText.error = null
        }

        if (genre.isEmpty()) {
            binding.genreSpinner.error = getString(R.string.required_field)
            isValid = false
        } else {
            binding.genreSpinner.error = null
        }

        if (ratingStr.isEmpty()) {
            binding.ratingEditText.error = getString(R.string.required_field)
            isValid = false
        } else {
            val rating = ratingStr.toFloatOrNull()
            if (rating == null || rating < 1 || rating > 10) {
                binding.ratingEditText.error = getString(R.string.invalid_rating)
                isValid = false
            } else {
                binding.ratingEditText.error = null
            }
        }

        if (yearStr.isEmpty()) {
            binding.yearEditText.error = getString(R.string.required_field)
            isValid = false
        } else {
            val year = yearStr.toIntOrNull()
            if (year == null || year < 1900 || year > 2030) {
                binding.yearEditText.error = getString(R.string.invalid_year)
                isValid = false
            } else {
                binding.yearEditText.error = null
            }
        }

        return isValid
    }
}
