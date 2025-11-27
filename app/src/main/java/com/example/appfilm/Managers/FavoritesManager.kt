package com.example.appfilm.Managers

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/**
 * Manages favorite films using SharedPreferences for persistent storage
 */
class FavoritesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("favorites", Context.MODE_PRIVATE)

    companion object {
        private const val FAVORITES_KEY = "fav_ids"
        private const val TAG = "FavoritesManager"
    }

    /**
     * Adds a film to favorites by its ID
     */
    fun addToFavorites(filmId: Int) {
        val currentFavorites = getFavoriteIds().toMutableSet()
        currentFavorites.add(filmId.toString())

        sharedPreferences.edit()
            .putStringSet(FAVORITES_KEY, currentFavorites)
            .apply()

        Log.d(TAG, "Added film $filmId to favorites")
    }

    /**
     * Removes a film from favorites by its ID
     */
    fun removeFromFavorites(filmId: Int) {
        val currentFavorites = getFavoriteIds().toMutableSet()
        currentFavorites.remove(filmId.toString())

        sharedPreferences.edit()
            .putStringSet(FAVORITES_KEY, currentFavorites)
            .apply()

        Log.d(TAG, "Removed film $filmId from favorites")
    }

    /**
     * Checks if a film is in favorites by its ID
     */
    fun isFavorite(filmId: Int): Boolean {
        return getFavoriteIds().contains(filmId.toString())
    }

    /**
     * Retrieves all favorite film IDs as a sorted list
     */
    fun getFavorites(): List<Int> {
        return getFavoriteIds()
            .mapNotNull { it.toIntOrNull() }
            .sorted()
    }

    /**
     * Clears all favorite films from storage
     */
    fun clearAllFavorites() {
        sharedPreferences.edit()
            .remove(FAVORITES_KEY)
            .apply()

        Log.d(TAG, "All favorites cleared")
    }

    /**
     * Returns the total count of favorite films
     */
    fun getFavoriteCount(): Int {
        return getFavoriteIds().size
    }

    /**
     * Retrieves favorite film IDs as a set of strings
     */
    private fun getFavoriteIds(): Set<String> {
        return sharedPreferences.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }
}