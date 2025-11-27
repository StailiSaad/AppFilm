package com.example.appfilm.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appfilm.Adapters.FavoritesAdapter
import com.example.appfilm.Managers.FavoritesManager
import com.example.appfilm.Models.Film
import com.example.appfilm.R

/**
 * Activity that displays and manages the user's list of favorite films.
 * Provides swipe-to-delete, clear-all, and navigation to film details.
 */
class FavoritesActivity : Activity() {

    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var emptyStateText: TextView
    private lateinit var backButton: ImageButton
    private lateinit var clearAllButton: Button

    private val favoriteFilms = mutableListOf<Film>()
    private lateinit var adapter: FavoritesAdapter
    private lateinit var favoritesManager: FavoritesManager

    /**
     * Initializes the activity and sets up UI components
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        favoritesManager = FavoritesManager(this)
        setupViews()
        setupRecyclerView()
        setupSwipeToDelete()
        setupButtons()
    }

    /**
     * Reloads favorites when activity resumes
     */
    override fun onResume() {
        super.onResume()
        loadFavorites()
    }

    /**
     * Initializes view references
     */
    private fun setupViews() {
        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView)
        emptyStateText = findViewById(R.id.emptyStateText)
        backButton = findViewById(R.id.backButton)
        clearAllButton = findViewById(R.id.clearAllButton)
    }

    /**
     * Configures the RecyclerView with adapter and layout manager
     */
    private fun setupRecyclerView() {
        favoritesRecyclerView.layoutManager = LinearLayoutManager(this)

        adapter = FavoritesAdapter(
            films = favoriteFilms,
            onItemClick = { film ->
                openFilmDetails(film)
            },
            onItemLongClick = { film, position ->
                removeFromFavorites(position)
            }
        )

        favoritesRecyclerView.adapter = adapter
    }

    /**
     * Sets up swipe-to-delete functionality for RecyclerView items
     */
    private fun setupSwipeToDelete() {
        val swipeToDeleteCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            /**
             * Handles item movement (not implemented)
             */
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            /**
             * Handles swipe gesture to remove item
             */
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                removeFromFavorites(position)
            }

            /**
             * Draws red background during swipe gesture
             */
            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val background = ColorDrawable(Color.RED)
                background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                background.draw(canvas)

                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(favoritesRecyclerView)
    }

    /**
     * Configures button click listeners
     */
    private fun setupButtons() {
        backButton.setOnClickListener {
            finish()
        }

        clearAllButton.setOnClickListener {
            if (favoriteFilms.isNotEmpty()) {
                favoriteFilms.clear()
                favoritesManager.clearAllFavorites()
                updateUI()
                Toast.makeText(this, "All favorites cleared", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Loads favorite films from storage and updates the list
     */
    private fun loadFavorites() {
        try {
            favoriteFilms.clear()

            val favoriteIds = favoritesManager.getFavorites()
            Log.d("FavoritesActivity", "Favorite IDs: $favoriteIds")

            val allFilms = getAllFilms()
            Log.d("FavoritesActivity", "All films count: ${allFilms.size}")

            favoriteFilms.addAll(allFilms.filter { film ->
                favoriteIds.contains(film.id)
            })

            Log.d("FavoritesActivity", "Favorite films count: ${favoriteFilms.size}")
            updateUI()

        } catch (e: Exception) {
            Log.e("FavoritesActivity", "Error loading favorites", e)
            Toast.makeText(this, "Error loading favorites", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Retrieves all available films from all categories
     */
    private fun getAllFilms(): List<Film> {
        val films = mutableListOf<Film>()
        films.addAll(getTrendingFilms())
        films.addAll(getPopularFilms())
        films.addAll(getNewReleases())
        films.addAll(getActionFilms())
        films.addAll(getComedyFilms())
        return films.distinctBy { it.id }
    }

    /**
     * Returns a list of trending films
     */
    private fun getTrendingFilms(): List<Film> {
        return listOf(
            Film(
                1,
                "Avengers: Endgame",
                "Action • Adventure",
                "The epic conclusion to the Infinity Saga",
                "3h 1m",
                8.4f,
                "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg"
            ),
            Film(
                2,
                "Spider-Man: No Way Home",
                "Action • Sci-Fi",
                "Multiverse adventure with Spider-Man",
                "2h 28m",
                8.2f,
                "https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg"
            ),
            Film(
                3, "The Batman", "Action • Crime", "Dark knight detective story", "2h 56m", 7.8f,
                "https://image.tmdb.org/t/p/w500/seyWFgGInaLqW7nOZvuJZCVRuP0.jpg"
            ),
            Film(
                16,
                "Black Widow",
                "Action • Adventure",
                "Natasha Romanoff confronts her past",
                "2h 14m",
                6.7f,
                "https://image.tmdb.org/t/p/w500/qAZ0pzat24kLdO3o8ejmbLxyOac.jpg"
            ),
            Film(
                17,
                "Shang-Chi",
                "Action • Fantasy",
                "Martial artist confronts his past",
                "2h 12m",
                7.4f,
                "https://image.tmdb.org/t/p/w500/1BIoJGKbXjdFDAqUEiA2VHqkK1Z.jpg"
            )
        )
    }

    /**
     * Returns a list of popular films
     */
    private fun getPopularFilms(): List<Film> {
        return listOf(
            Film(
                4, "Dune", "Sci-Fi • Adventure", "Desert planet epic adventure", "2h 35m", 8.0f,
                "https://image.tmdb.org/t/p/w500/d5NXSklXo0qyIYkgV94XAgMIckC.jpg"
            ),
            Film(
                5,
                "Top Gun: Maverick",
                "Action • Drama",
                "High-flying sequel to classic",
                "2h 11m",
                8.6f,
                "https://image.tmdb.org/t/p/w500/62HCnUTziyWcpDaBO2i1DX17ljH.jpg"
            ),
            Film(
                6, "Black Panther", "Action • Adventure", "Wakanda superhero story", "2h 14m", 7.3f,
                "https://image.tmdb.org/t/p/w500/uxzzxijgPIY7slzFvMotPv8wjKA.jpg"
            ),
            Film(
                25,
                "Wonder Woman",
                "Action • Adventure",
                "Amazon princess saves world",
                "2h 21m",
                7.4f,
                "https://image.tmdb.org/t/p/w500/imekS7f1OuHyUP2LAiTEM0zBzUz.jpg"
            ),
            Film(
                26,
                "Jurassic World",
                "Action • Adventure",
                "Dinosaur theme park disaster",
                "2h 4m",
                7.0f,
                "https://image.tmdb.org/t/p/w500/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg"
            )
        )
    }

    /**
     * Returns a list of new release films
     */
    private fun getNewReleases(): List<Film> {
        return listOf(
            Film(
                7,
                "Avatar: The Way of Water",
                "Sci-Fi • Adventure",
                "Underwater alien world",
                "3h 12m",
                7.6f,
                "https://image.tmdb.org/t/p/w500/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg"
            ),
            Film(
                8, "John Wick 4", "Action • Thriller", "Assassin revenge story", "2h 49m", 7.7f,
                "https://image.tmdb.org/t/p/w500/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg"
            ),
            Film(
                9, "Oppenheimer", "Biography • Drama", "Atomic bomb creation story", "3h 0m", 8.3f,
                "https://image.tmdb.org/t/p/w500/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg"
            ),
            Film(
                34, "Barbie", "Comedy • Adventure", "Barbie ventures to real world", "1h 54m", 7.8f,
                "https://image.tmdb.org/t/p/w500/iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg"
            ),
            Film(
                35,
                "Mission Impossible 7",
                "Action • Adventure",
                "Ethan Hunt's deadliest mission",
                "2h 43m",
                7.8f,
                "https://image.tmdb.org/t/p/w500/NNxYkU70HPurnNCSiCjYAmacwm.jpg"
            )
        )
    }

    /**
     * Returns a list of action films
     */
    private fun getActionFilms(): List<Film> {
        return listOf(
            Film(
                10,
                "Mission Impossible",
                "Action • Adventure",
                "Spy action thriller",
                "2h 23m",
                7.1f,
                "https://image.tmdb.org/t/p/w500/vkjsoMF86dJIvNrSEmYQRVxDFxh.jpg"
            ),
            Film(
                11, "Fast & Furious", "Action • Crime", "Car racing family story", "2h 23m", 6.8f,
                "https://image.tmdb.org/t/p/w500/dkMD69qHuu6U4Z1qk01E34hVfwp.jpg"
            ),
            Film(
                12, "The Dark Knight", "Action • Crime", "Batman vs Joker classic", "2h 32m", 9.0f,
                "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
            ),
            Film(
                43,
                "Die Hard",
                "Action • Thriller",
                "Cop battles terrorists in skyscraper",
                "2h 12m",
                8.2f,
                "https://image.tmdb.org/t/p/w500/yFihWxQcmqcaBR31QM6Y8gT6aYV.jpg"
            ),
            Film(
                44,
                "Terminator 2",
                "Action • Sci-Fi",
                "Cyborg protects future leader",
                "2h 17m",
                8.6f,
                "https://image.tmdb.org/t/p/w500/5M0j0B18abtBI5gi2RhfjjurTqb.jpg"
            )
        )
    }

    /**
     * Returns a list of comedy films
     */
    private fun getComedyFilms(): List<Film> {
        return listOf(
            Film(
                13, "Superbad", "Comedy • Teen", "High school comedy adventure", "1h 53m", 7.6f,
                "https://image.tmdb.org/t/p/w500/ek8e8txUyUwd2BNqj6lFEerJVPu.jpg"
            ),
            Film(
                14,
                "The Hangover",
                "Comedy • Mystery",
                "Vegas bachelor party gone wrong",
                "1h 40m",
                7.7f,
                "https://image.tmdb.org/t/p/w500/uluhlXubGu1VxU63X9VHCLWDAYP.jpg"
            ),
            Film(
                15,
                "Step Brothers",
                "Comedy • Family",
                "Adult step brothers comedy",
                "1h 38m",
                6.9f,
                "https://image.tmdb.org/t/p/w500/dSsSjS14AHO7K2g8hqJCxL2J60c.jpg"
            ),
            Film(
                52, "Bridesmaids", "Comedy • Romance", "Wedding preparations chaos", "2h 5m", 6.8f,
                "https://image.tmdb.org/t/p/w500/kVrU0p6tqaQdqaZkHlsPsJs2zVv.jpg"
            ),
            Film(
                53, "Anchorman", "Comedy • Satire", "1970s news anchor rivalry", "1h 34m", 7.1f,
                "https://image.tmdb.org/t/p/w500/zMucLbxkI4gqgqkjv6YF2ZkXZx8.jpg"
            )
        )
    }

    /**
     * Removes a film from favorites at the specified position
     */
    private fun removeFromFavorites(position: Int) {
        if (position < favoriteFilms.size) {
            val film = favoriteFilms[position]
            favoritesManager.removeFromFavorites(film.id)
            favoriteFilms.removeAt(position)
            adapter.updateFilms(favoriteFilms)
            updateUI()
            Toast.makeText(this, "${film.title} removed from favorites", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Updates the UI based on whether favorites list is empty or not
     */
    private fun updateUI() {
        if (favoriteFilms.isEmpty()) {
            emptyStateText.visibility = View.VISIBLE
            favoritesRecyclerView.visibility = View.GONE
            clearAllButton.visibility = View.GONE
        } else {
            emptyStateText.visibility = View.GONE
            favoritesRecyclerView.visibility = View.VISIBLE
            clearAllButton.visibility = View.VISIBLE
            adapter.updateFilms(favoriteFilms)
        }
    }

    /**
     * Opens FilmDetailsActivity for the selected film
     */
    private fun openFilmDetails(film: Film) {
        val intent = Intent(this, FilmDetailsActivity::class.java)
        intent.putExtra("film_id", film.id)
        intent.putExtra("film_title", film.title)
        intent.putExtra("film_type", film.type)
        intent.putExtra("film_description", film.description)
        intent.putExtra("film_rating", film.rating)
        intent.putExtra("film_duration", film.duration)
        startActivity(intent)
    }
}