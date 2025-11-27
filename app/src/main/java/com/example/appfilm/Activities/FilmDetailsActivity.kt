package com.example.appfilm.Activities

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.example.appfilm.Managers.FavoritesManager
import com.example.appfilm.Models.Film
import com.example.appfilm.R
import com.squareup.picasso.Picasso

/**
 * Activity that displays detailed information about a selected film
 * Allows users to add/remove films from favorites and manage watch lists
 */
class FilmDetailsActivity : Activity() {

    private lateinit var backButton: ImageButton
    private lateinit var filmBackdrop: ImageView
    private lateinit var filmTitle: TextView
    private lateinit var filmType: TextView
    private lateinit var filmRating: TextView
    private lateinit var filmDescription: TextView
    private lateinit var addToListRadioGroup: RadioGroup
    private lateinit var confirmButton: Button
    private lateinit var watchButton: Button
    private lateinit var favoriteButton: ImageButton

    private lateinit var favoritesManager: FavoritesManager
    private var isFavorite = false

    private var currentFilm: Film? = null

    /**
     * Initializes the activity and sets up film data and UI components
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_details)

        favoritesManager = FavoritesManager(this)

        setupViews()
        setupFilmData()
        setupButtons()
    }

    /**
     * Initializes all view references from the layout
     */
    private fun setupViews() {
        backButton = findViewById(R.id.backButton)
        filmBackdrop = findViewById(R.id.filmBackdrop)
        filmTitle = findViewById(R.id.filmTitle)
        filmType = findViewById(R.id.filmType)
        filmRating = findViewById(R.id.filmRating)
        filmDescription = findViewById(R.id.filmDescription)
        addToListRadioGroup = findViewById(R.id.addToListRadioGroup)
        confirmButton = findViewById(R.id.confirmButton)
        watchButton = findViewById(R.id.watchButton)
        favoriteButton = findViewById(R.id.favoriteButton)
    }

    /**
     * Extracts film data from intent and populates the UI
     */
    private fun setupFilmData() {

        val id = intent.getIntExtra("film_id", 0)
        val title = intent.getStringExtra("film_title") ?: ""
        val type = intent.getStringExtra("film_type") ?: ""
        val description = intent.getStringExtra("film_description") ?: ""
        val rating = intent.getFloatExtra("film_rating", 0f)
        val duration = intent.getStringExtra("film_duration") ?: ""
        val imageUrl = intent.getStringExtra("film_image") ?: ""

        currentFilm = Film(id, title, type, description, duration, rating, imageUrl)

        filmTitle.text = title
        filmType.text = "$type • $duration"
        filmRating.text = "$rating/10"
        filmDescription.text = description

        // Load image into backdrop
        if (imageUrl.isNotEmpty()) {
            Picasso.get()
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_close_clear_cancel)
                .fit()
                .centerCrop()
                .into(filmBackdrop)
        }

        isFavorite = favoritesManager.isFavorite(id)
        updateFavoriteButton()
    }

    /**
     * Sets up click listeners for all interactive buttons
     */
    private fun setupButtons() {

        backButton.setOnClickListener { finish() }

        confirmButton.setOnClickListener {
            val checked = addToListRadioGroup.checkedRadioButtonId

            currentFilm?.let { film ->
                if (checked == R.id.radioYes) {
                    favoritesManager.addToFavorites(film.id)
                    isFavorite = true
                } else {
                    favoritesManager.removeFromFavorites(film.id)
                    isFavorite = false
                }
                updateFavoriteButton()
                Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show()
            }
        }

        favoriteButton.setOnClickListener {
            currentFilm?.let { film ->
                isFavorite = !isFavorite
                if (isFavorite) favoritesManager.addToFavorites(film.id)
                else favoritesManager.removeFromFavorites(film.id)

                updateFavoriteButton()
            }
        }
    }

    /**
     * Updates the favorite button icon based on current favorite status
     */
    private fun updateFavoriteButton() {
        favoriteButton.setImageResource(
            if (isFavorite) android.R.drawable.btn_star_big_on
            else android.R.drawable.btn_star_big_off
        )
    }
}