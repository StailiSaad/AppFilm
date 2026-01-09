package com.example.tpl0part2.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class representing a film in the application.
 * 
 * This class holds all relevant information about a film including its metadata
 * and user-specific data like watch priority.
 * 
 * @property id Unique identifier for the film
 * @property title Title of the film
 * @property description Brief description or synopsis
 * @property genre Film genre (Action, Comedy, etc.)
 * @property rating Average rating (1.0 to 10.0)
 * @property year Release year
 * @property imageUrl URL to the film's poster image
 * @property watchPriority User's priority for watching this film
 */
@Parcelize
data class Film(
    val id: Int,
    val title: String,
    val description: String,
    val genre: String,
    val rating: Float,
    val year: Int,
    val imageUrl: String,
    val watchPriority: Priority = Priority.MEDIUM
) : Parcelable {

    /**
     * Enum representing the watch priority level for a film.
     * 
     * Users can categorize films by priority to organize their watchlist.
     */
    enum class Priority {
        /** High priority - Watch first */
        HIGH,
        
        /** Medium priority - Watch when available */
        MEDIUM,
        
        /** Low priority - Watch later */
        LOW
    }

    /**
     * Gets a display string for the film's type/category.
     * 
     * @return Formatted string combining genre and year
     */
    fun getTypeString(): String {
        return "$genre • $year"
    }

    /**
     * Gets a formatted rating string.
     * 
     * @return Rating formatted to one decimal place with "/10" suffix
     */
    fun getRatingString(): String {
        return String.format("%.1f/10", rating)
    }
}
