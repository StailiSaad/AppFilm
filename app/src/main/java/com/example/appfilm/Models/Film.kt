package com.example.appfilm.Models

/**
 * Data class representing a film with basic information and metadata
 */
data class Film(
    val id: Int,
    val title: String,
    val type: String,
    val description: String,
    val duration: String,
    val rating: Float,
    val imageUrl: String = ""
)