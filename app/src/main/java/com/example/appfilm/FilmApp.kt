/**
 * Application class that provides global access to FavoritesManager instance
 */
package com.example.appfilm

import android.app.Application
import com.example.appfilm.Managers.FavoritesManager

class FilmApp : Application() {
    val favoritesManager by lazy { FavoritesManager(this) }
}