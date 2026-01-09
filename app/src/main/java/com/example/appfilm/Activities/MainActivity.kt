package com.example.tpl0part2.Activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpl0part2.Adapters.FilmAdapter
import com.example.tpl0part2.Models.Film
import com.example.tpl0part2.databinding.ActivityMainBinding

/**
 * Main activity serving as the home screen of the application.
 * 
 * This activity displays multiple categories of films in horizontal RecyclerViews
 * and provides search/filter functionality. It implements the main navigation
 * and film browsing features.
 * 
 * @property filmAdapter Adapter for displaying films in RecyclerView
 * @property allFilms Complete list of all films (unfiltered)
 * @property currentFilms Currently displayed films (filtered)
 */
class MainActivity : AppCompatActivity(), FilmAdapter.OnFilmClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var filmAdapter: FilmAdapter
    private var allFilms = mutableListOf<Film>()
    private var currentFilms = mutableListOf<Film>()
    private var isFiltering = false

    /**
     * Called when the activity is first created.
     * 
     * @param savedInstanceState If the activity is being re-initialized after previously
     * being shut down then this Bundle contains the data it most recently supplied.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadFilms()
        setupSearch()
        setupNavigation()
    }

    /**
     * Sets up the main films RecyclerView with horizontal layout.
     */
    private fun setupRecyclerView() {
        filmAdapter = FilmAdapter(currentFilms, this)
        binding.mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = filmAdapter
            setHasFixedSize(true)
        }
        
        // Add item animation
        binding.mainRecyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
    }

    /**
     * Loads sample film data into the application.
     * 
     * In a production app, this would typically load data from a database or API.
     */
    private fun loadFilms() {
        allFilms.clear()
        allFilms.addAll(getSampleFilms())
        currentFilms.clear()
        currentFilms.addAll(allFilms)
        filmAdapter.notifyDataSetChanged()
        updateEmptyState()
    }

    /**
     * Generates sample film data for demonstration purposes.
     * 
     * @return List of sample Film objects
     */
    private fun getSampleFilms(): List<Film> {
        return listOf(
            Film(1, "Inception", "A thief who steals corporate secrets", "Action", 8.8f, 2010, "", Film.Priority.HIGH),
            Film(2, "Interstellar", "A team of explorers travel through a wormhole", "Sci-Fi", 8.6f, 2014, "", Film.Priority.MEDIUM),
            Film(3, "The Dark Knight", "Batman faces the Joker", "Action", 9.0f, 2008, "", Film.Priority.HIGH),
            Film(4, "Pulp Fiction", "The lives of two mob hitmen", "Crime", 8.9f, 1994, "", Film.Priority.MEDIUM),
            Film(5, "Parasite", "A poor family schemes to become employed", "Drama", 8.6f, 2019, "", Film.Priority.LOW)
        )
    }

    /**
     * Sets up the search functionality with TextWatcher.
     */
    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filterFilms(s.toString())
                binding.clearSearchButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }
        })

        binding.clearSearchButton.setOnClickListener {
            binding.searchEditText.text.clear()
            binding.clearSearchButton.visibility = View.GONE
        }
    }

    /**
     * Filters films based on search query.
     * 
     * @param query Search string to filter films by title or genre
     */
    private fun filterFilms(query: String) {
        isFiltering = query.isNotEmpty()
        
        currentFilms.clear()
        if (query.isEmpty()) {
            currentFilms.addAll(allFilms)
        } else {
            val filtered = allFilms.filter { film ->
                film.title.contains(query, true) || film.genre.contains(query, true)
            }
            currentFilms.addAll(filtered)
        }
        
        filmAdapter.notifyDataSetChanged()
        updateEmptyState()
    }

    /**
     * Sets up bottom navigation button listeners.
     */
    private fun setupNavigation() {
        binding.homeNavButton.setOnClickListener { /* Already on home */ }
        binding.premiumNavButton.setOnClickListener { startActivity(Intent(this, PaymentActivity::class.java)) }
        binding.favoritesNavButton.setOnClickListener { startActivity(Intent(this, FavoritesActivity::class.java)) }
        binding.loginButton.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
    }

    /**
     * Updates the empty state visibility based on current film list.
     */
    private fun updateEmptyState() {
        if (isFiltering && currentFilms.isEmpty()) {
            binding.emptyStateText.visibility = View.VISIBLE
            binding.emptyStateText.text = "No films found for your search"
            binding.mainRecyclerView.visibility = View.GONE
        } else if (currentFilms.isEmpty()) {
            binding.emptyStateText.visibility = View.VISIBLE
            binding.emptyStateText.text = "No films available\n\nAdd some films to get started!"
            binding.mainRecyclerView.visibility = View.GONE
        } else {
            binding.emptyStateText.visibility = View.GONE
            binding.mainRecyclerView.visibility = View.VISIBLE
        }
    }

    /**
     * Handles film item clicks from the adapter.
     * 
     * @param film The film that was clicked
     */
    override fun onFilmClick(film: Film) {
        val intent = Intent(this, FilmDetailsActivity::class.java).apply {
            putExtra("film", film)
        }
        startActivity(intent)
    }

    /**
     * Initializes the options menu.
     * 
     * @param menu The options menu in which items are placed
     * @return True to display the menu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        
        // Setup search in menu
        val searchItem = menu.findItem(R.id.action_filter)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_film)
        
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterFilms(newText ?: "")
                return true
            }
        })
        
        return true
    }

    /**
     * Handles options menu item selections.
     * 
     * @param item The menu item that was selected
     * @return True if the event was handled, false otherwise
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                startActivityForResult(Intent(this, FormActivity::class.java), ADD_FILM_REQUEST)
                true
            }
            R.id.action_sort -> {
                showSortDialog()
                true
            }
            R.id.action_settings -> {
                // Open settings activity or dialog
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Shows a dialog for sorting options.
     */
    private fun showSortDialog() {
        android.app.AlertDialog.Builder(this)
            .setTitle("Sort Films")
            .setItems(arrayOf("By Title", "By Rating", "By Year")) { _, which ->
                when (which) {
                    0 -> sortFilmsByTitle()
                    1 -> sortFilmsByRating()
                    2 -> sortFilmsByYear()
                }
            }
            .show()
    }

    /**
     * Sorts films alphabetically by title.
     */
    private fun sortFilmsByTitle() {
        currentFilms.sortBy { it.title }
        filmAdapter.notifyDataSetChanged()
    }

    /**
     * Sorts films by rating (highest first).
     */
    private fun sortFilmsByRating() {
        currentFilms.sortByDescending { it.rating }
        filmAdapter.notifyDataSetChanged()
    }

    /**
     * Sorts films by release year (newest first).
     */
    private fun sortFilmsByYear() {
        currentFilms.sortByDescending { it.year }
        filmAdapter.notifyDataSetChanged()
    }

    /**
     * Handles activity results from FormActivity.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_FILM_REQUEST && resultCode == RESULT_OK) {
            val film = data?.getSerializableExtra("film") as? Film
            film?.let {
                allFilms.add(it)
                currentFilms.add(it)
                filmAdapter.notifyItemInserted(currentFilms.size - 1)
                updateEmptyState()
            }
        }
    }

    companion object {
        private const val ADD_FILM_REQUEST = 1001
    }
}
