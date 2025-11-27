package com.example.appfilm.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.appfilm.Models.Film
import com.example.appfilm.R
import com.squareup.picasso.Picasso

/**
 * Main activity displaying film categories with horizontal scrolling lists
 * Handles navigation, search functionality, and film selection
 */
class MainActivity : Activity() {

    private lateinit var trendingLayout: LinearLayout
    private lateinit var popularLayout: LinearLayout
    private lateinit var newReleasesLayout: LinearLayout
    private lateinit var actionLayout: LinearLayout
    private lateinit var comedyLayout: LinearLayout

    private lateinit var searchEditText: EditText
    private lateinit var clearSearchButton: ImageButton

    private lateinit var homeNavButton: LinearLayout
    private lateinit var premiumNavButton: LinearLayout
    private lateinit var favoritesNavButton: LinearLayout
    private lateinit var loginButton: LinearLayout

    /**
     * Initializes the main activity and sets up UI components
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupHorizontalLists()
        setupSearchBar()
        setupBottomNavigation()
    }

    /**
     * Initializes all view references from the layout
     */
    private fun setupViews() {
        trendingLayout = findViewById(R.id.trendingLayout)
        popularLayout = findViewById(R.id.popularLayout)
        newReleasesLayout = findViewById(R.id.newReleasesLayout)
        actionLayout = findViewById(R.id.actionLayout)
        comedyLayout = findViewById(R.id.comedyLayout)

        searchEditText = findViewById(R.id.searchEditText)
        clearSearchButton = findViewById(R.id.clearSearchButton)

        homeNavButton = findViewById(R.id.homeNavButton)
        premiumNavButton = findViewById(R.id.premiumNavButton)
        favoritesNavButton = findViewById(R.id.favoritesNavButton)
        loginButton = findViewById(R.id.loginButton)
    }

    /**
     * Sets up horizontal film lists for each category
     */
    private fun setupHorizontalLists() {
        setupFilmCategory(trendingLayout, getTrendingFilms())
        setupFilmCategory(popularLayout, getPopularFilms())
        setupFilmCategory(newReleasesLayout, getNewReleases())
        setupFilmCategory(actionLayout, getActionFilms())
        setupFilmCategory(comedyLayout, getComedyFilms())
    }

    /**
     * Populates a layout with film views for a specific category
     */
    private fun setupFilmCategory(layout: LinearLayout, films: List<Film>) {
        layout.removeAllViews()

        for (film in films) {
            val filmView = createFilmView(film)
            layout.addView(filmView)
        }
    }

    /**
     * Creates and configures a view for a single film item
     */
    private fun createFilmView(film: Film): View {
        val inflater = layoutInflater
        val filmView = inflater.inflate(R.layout.item_film, null)

        val filmImage = filmView.findViewById<ImageView>(R.id.filmImage)
        val filmTitle = filmView.findViewById<TextView>(R.id.filmTitle)
        val filmType = filmView.findViewById<TextView>(R.id.filmType)
        val filmRating = filmView.findViewById<TextView>(R.id.filmRating)

        ///// Load image from URL using Picasso
        if (film.imageUrl.isNotEmpty()) {
            Picasso.get()
                .load(film.imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .fit()
                .centerCrop()
                .into(filmImage)
        } else {
            filmImage.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        filmTitle.text = film.title
        filmType.text = film.type
        filmRating.text = "⭐ ${film.rating}"

        filmView.setOnClickListener {
            openFilmDetails(film)
        }

        ////HORIZONTAL LIST
        val layoutParams = LinearLayout.LayoutParams(
            resources.getDimensionPixelSize(R.dimen.film_item_width),
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        filmView.layoutParams = layoutParams

        return filmView
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
        intent.putExtra("film_image", film.imageUrl)
        startActivity(intent)
    }

    /**
     * Returns a list of trending films
     */
    private fun getTrendingFilms(): List<Film> {
        return listOf(
            Film(
                1, "Avengers: Endgame", "Action • Adventure",
                "The epic conclusion to the Infinity Saga", "3h 1m", 8.4f,
                "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg"
            ),

            Film(
                2, "Spider-Man: No Way Home", "Action • Sci-Fi",
                "Multiverse adventure with Spider-Man", "2h 28m", 8.2f,
                "https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg"
            ),

            Film(
                3, "The Batman", "Action • Crime",
                "Dark knight detective story", "2h 56m", 7.8f,
                "https://image.tmdb.org/t/p/w500/seyWFgGInaLqW7nOZvuJZCVRuP0.jpg"
            ),

            Film(
                16, "Black Widow", "Action • Adventure",
                "Natasha Romanoff confronts her past", "2h 14m", 6.7f,
                "https://image.tmdb.org/t/p/w500/qAZ0pzat24kLdO3o8ejmbLxyOac.jpg"
            ),

            Film(
                17, "Shang-Chi", "Action • Fantasy",
                "Martial artist confronts his past", "2h 12m", 7.4f,
                "https://image.tmdb.org/t/p/w500/1BIoJGKbXjdFDAqUEiA2VHqkK1Z.jpg"
            ),

            Film(
                18, "Eternals", "Action • Fantasy",
                "Ancient aliens protect Earth", "2h 37m", 6.3f,
                "https://image.tmdb.org/t/p/w500/bcCBq9N1EMo3daNIjWJ8kYvrQm6.jpg"
            ),

            Film(
                19, "Doctor Strange 2", "Action • Fantasy",
                "Multiverse of madness", "2h 6m", 6.9f,
                "https://image.tmdb.org/t/p/w500/9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg"
            ),

            Film(
                20, "Thor: Love and Thunder", "Action • Comedy",
                "Thor's new adventure", "1h 59m", 6.2f,
                "https://image.tmdb.org/t/p/w500/pIkRyD18kl4FhoCNQuWxWu5cBLM.jpg"
            ),

            Film(
                21, "Black Adam", "Action • Fantasy",
                "Anti-hero emerges in DC universe", "2h 5m", 6.3f,
                "https://image.tmdb.org/t/p/w500/pFlaoHTZeyNkG83vxsAJiGzfSsa.jpg"
            ),

            Film(
                22, "The Flash", "Action • Adventure",
                "Speedster alters timeline", "2h 24m", 6.8f,
                "https://image.tmdb.org/t/p/w500/rktDFPbfHfUbArZ6OOOKsXcv0Bm.jpg"
            )
        )
    }

    /**
     * Returns a list of popular films
     */
    private fun getPopularFilms(): List<Film> {
        return listOf(
            Film(
                4, "Dune", "Sci-Fi • Adventure",
                "Desert planet epic adventure", "2h 35m", 8.0f,
                "https://image.tmdb.org/t/p/w500/d5NXSklXo0qyIYkgV94XAgMIckC.jpg"
            ),

            Film(
                5, "Top Gun: Maverick", "Action • Drama",
                "High-flying sequel to classic", "2h 11m", 8.6f,
                "https://image.tmdb.org/t/p/w500/62HCnUTziyWcpDaBO2i1DX17ljH.jpg"
            ),

            Film(
                6, "Black Panther", "Action • Adventure",
                "Wakanda superhero story", "2h 14m", 7.3f,
                "https://image.tmdb.org/t/p/w500/uxzzxijgPIY7slzFvMotPv8wjKA.jpg"
            ),

            Film(
                25, "Wonder Woman", "Action • Adventure",
                "Amazon princess saves world", "2h 21m", 7.4f,
                "https://image.tmdb.org/t/p/w500/imekS7f1OuHyUP2LAiTEM0zBzUz.jpg"
            ),

            Film(
                26, "Jurassic World", "Action • Adventure",
                "Dinosaur theme park disaster", "2h 4m", 7.0f,
                "https://image.tmdb.org/t/p/w500/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg"
            ),

            Film(
                27, "The Matrix", "Sci-Fi • Action",
                "Hacker discovers reality", "2h 16m", 8.7f,
                "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg"
            ),

            Film(
                28, "Inception", "Sci-Fi • Thriller",
                "Dream within a dream heist", "2h 28m", 8.8f,
                "https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg"
            ),

            Film(
                29, "Interstellar", "Sci-Fi • Drama",
                "Space travel to save humanity", "2h 49m", 8.6f,
                "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg"
            ),

            Film(
                30, "The Dark Knight", "Action • Crime",
                "Batman vs Joker", "2h 32m", 9.0f,
                "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
            ),

            Film(
                31, "Gladiator", "Action • Drama",
                "Roman general seeks revenge", "2h 35m", 8.5f,
                "https://image.tmdb.org/t/p/w500/ty8TGRuvJLPUmAR1H1nRIsgwvim.jpg"
            )
        )
    }

    /**
     * Returns a list of new release films
     */
    private fun getNewReleases(): List<Film> {
        return listOf(
            Film(
                7, "Avatar: The Way of Water", "Sci-Fi • Adventure",
                "Underwater alien world", "3h 12m", 7.6f,
                "https://image.tmdb.org/t/p/w500/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg"
            ),

            Film(
                8, "John Wick 4", "Action • Thriller",
                "Assassin revenge story", "2h 49m", 7.7f,
                "https://image.tmdb.org/t/p/w500/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg"
            ),

            Film(
                9, "Oppenheimer", "Biography • Drama",
                "Atomic bomb creation story", "3h 0m", 8.3f,
                "https://image.tmdb.org/t/p/w500/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg"
            ),

            Film(
                34, "Barbie", "Comedy • Adventure",
                "Barbie ventures to real world", "1h 54m", 7.8f,
                "https://image.tmdb.org/t/p/w500/iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg"
            ),

            Film(
                35, "Mission Impossible 7", "Action • Adventure",
                "Ethan Hunt's deadliest mission", "2h 43m", 7.8f,
                "https://image.tmdb.org/t/p/w500/NNxYkU70HPurnNCSiCjYAmacwm.jpg"
            ),

            Film(
                36, "The Little Mermaid", "Fantasy • Musical",
                "Mermaid dreams of human world", "2h 15m", 7.2f,
                "https://image.tmdb.org/t/p/w500/ym1dxyOk4jFcSl4Q2zmRrA5BEEC.jpg"
            ),

            Film(
                37, "Fast X", "Action • Thriller",
                "Dom Toretto's final ride", "2h 21m", 6.5f,
                "https://image.tmdb.org/t/p/w500/fiVW06jE7z9YnO4trhaMEdclSiC.jpg"
            ),

            Film(
                38, "Transformers: Rise of the Beasts", "Action • Sci-Fi",
                "New Transformers adventure", "2h 7m", 6.1f,
                "https://image.tmdb.org/t/p/w500/gPbM0MK8CP8A174rmUwGsADNYKD.jpg"
            ),

            Film(
                39, "Indiana Jones 5", "Action • Adventure",
                "Archaeologist's final journey", "2h 34m", 6.7f,
                "https://image.tmdb.org/t/p/w500/Af4bXE63pVsb2FtbW8uYIyPBadD.jpg"
            ),

            Film(
                40, "The Marvels", "Action • Adventure",
                "Carol Danvers teams up", "1h 45m", 6.5f,
                "https://image.tmdb.org/t/p/w500/9GBhzXMFjgcZ3FdR9w3bUMMTps5.jpg"
            )
        )
    }

    /**
     * Returns a list of action films
     */
    private fun getActionFilms(): List<Film> {
        return listOf(
            Film(
                10, "Mission Impossible", "Action • Adventure",
                "Spy action thriller", "2h 23m", 7.1f,
                "https://image.tmdb.org/t/p/w500/vkjsoMF86dJIvNrSEmYQRVxDFxh.jpg"
            ),

            Film(
                11, "Fast & Furious", "Action • Crime",
                "Car racing family story", "2h 23m", 6.8f,
                "https://image.tmdb.org/t/p/w500/dkMD69qHuu6U4Z1qk01E34hVfwp.jpg"
            ),

            Film(
                12, "The Dark Knight", "Action • Crime",
                "Batman vs Joker classic", "2h 32m", 9.0f,
                "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
            ),

            Film(
                43, "Die Hard", "Action • Thriller",
                "Cop battles terrorists", "2h 12m", 8.2f,
                "https://image.tmdb.org/t/p/w500/yFihWxQcmqcaBR31QM6Y8gT6aYV.jpg"
            ),

            Film(
                44, "Terminator 2", "Action • Sci-Fi",
                "Cyborg protects future leader", "2h 17m", 8.6f,
                "https://image.tmdb.org/t/p/w500/5M0j0B18abtBI5gi2RhfjjurTqb.jpg"
            ),

            Film(
                45, "Predator", "Action • Sci-Fi",
                "Special forces vs alien hunter", "1h 47m", 7.8f,
                "https://image.tmdb.org/t/p/w500/k3mW4qfJo6Skhrr2rYIYbF4NHei.jpg"
            ),

            Film(
                46, "The Raid", "Action • Crime",
                "SWAT team raids drug lord", "1h 41m", 7.6f,
                "https://image.tmdb.org/t/p/w500/7lTnUAfzVYV3VH3nVvVU7vNnFtp.jpg"
            ),

            Film(
                47, "Kill Bill", "Action • Thriller",
                "Assassin seeks revenge", "1h 51m", 8.1f,
                "https://image.tmdb.org/t/p/w500/v7TaX8kXMXs5yFFGR41guUDNcnB.jpg"
            ),

            Film(
                48, "The Bourne Identity", "Action • Thriller",
                "Amnesiac assassin discovers past", "1h 59m", 7.9f,
                "https://image.tmdb.org/t/p/w500/bXQIL36VQdzJ69lcjQR1WQzJqQR.jpg"
            ),

            Film(
                49, "Taken", "Action • Thriller",
                "Father rescues kidnapped daughter", "1h 33m", 7.8f,
                "https://image.tmdb.org/t/p/w500/gQXCWwblYpVenSTDjn1bpl32kL7.jpg"
            )
        )
    }

    /**
     * Returns a list of comedy films
     */
    private fun getComedyFilms(): List<Film> {
        return listOf(
            Film(
                13, "Superbad", "Comedy • Teen",
                "High school comedy adventure", "1h 53m", 7.6f,
                "https://image.tmdb.org/t/p/w500/ek8e8txUyUwd2BNqj6lFEerJVPu.jpg"
            ),

            Film(
                14, "The Hangover", "Comedy • Mystery",
                "Vegas bachelor party gone wrong", "1h 40m", 7.7f,
                "https://image.tmdb.org/t/p/w500/uluhlXubGu1VxU63X9VHCLWDAYP.jpg"
            ),

            Film(
                15, "Step Brothers", "Comedy • Family",
                "Adult step brothers comedy", "1h 38m", 6.9f,
                "https://image.tmdb.org/t/p/w500/dSsSjS14AHO7K2g8hqJCxL2J60c.jpg"
            ),

            Film(
                52, "Bridesmaids", "Comedy • Romance",
                "Wedding preparation chaos", "2h 5m", 6.8f,
                "https://image.tmdb.org/t/p/w500/kVrU0p6tqaQdqaZkHlsPsJs2zVv.jpg"
            ),

            Film(
                53, "Anchorman", "Comedy • Satire",
                "1970s news anchor rivalry", "1h 34m", 7.1f,
                "https://image.tmdb.org/t/p/w500/zMucLbxkI4gqgqkjv6YF2ZkXZx8.jpg"
            ),

            Film(
                54, "21 Jump Street", "Comedy • Action",
                "Cops go undercover in high school", "1h 49m", 7.2f,
                "https://image.tmdb.org/t/p/w500/wn3MPzCjgY9rVo2bO1e7AtBqK5q.jpg"
            ),

            Film(
                55, "The Other Guys", "Comedy • Action",
                "Desk cops become heroes", "1h 47m", 6.6f,
                "https://image.tmdb.org/t/p/w500/eFTuS7gMCmQYC20ACwTzxIh6AcL.jpg"
            ),

            Film(
                56, "Ted", "Comedy • Fantasy",
                "Man and his talking teddy bear", "1h 46m", 6.9f,
                "https://image.tmdb.org/t/p/w500/yLdP2vDa1Bqx1sX2B2Q0D9i9Mnj.jpg"
            ),

            Film(
                57, "Pitch Perfect", "Comedy • Music",
                "College a cappella group", "1h 52m", 7.1f,
                "https://image.tmdb.org/t/p/w500/l8Qbu1A8LkGq9U0vL4a4eJZbbTG.jpg"
            ),

            Film(
                58, "Game Night", "Comedy • Mystery",
                "Game night turns real", "1h 40m", 6.9f,
                "https://image.tmdb.org/t/p/w500/qmixX8s2PG8Tcf9mYm2ZvRC1Z34.jpg"
            )
        )
    }

    /**
     * Sets up search bar functionality with text change listeners
     */
    private fun setupSearchBar() {
        searchEditText.setOnFocusChangeListener { _, hasFocus ->
            clearSearchButton.visibility = if (hasFocus || searchEditText.text.isNotEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                clearSearchButton.visibility = if (s.isNullOrEmpty()) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
        })

        clearSearchButton.setOnClickListener {
            searchEditText.text.clear()
            searchEditText.clearFocus()
        }
    }

    /**
     * Sets up bottom navigation with click listeners
     */
    private fun setupBottomNavigation() {
        homeNavButton.setOnClickListener {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
        }

        premiumNavButton.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }

        favoritesNavButton.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
        loginButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}