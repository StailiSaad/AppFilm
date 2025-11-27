package com.example.appfilm.Adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.appfilm.Activities.FilmDetailsActivity
import com.example.appfilm.Models.Film
import com.example.appfilm.R
import com.squareup.picasso.Picasso

/**
 * ArrayAdapter for displaying films in a ListView with click handling
 */
class FilmAdapter(
    private val context: Activity,
    private val films: List<Film>
) : ArrayAdapter<Film>(context, R.layout.item_film, films) {

    /**
     * Creates and returns the view for each film item in the list
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_film, parent, false)

        val film = films[position]

        val img = view.findViewById<ImageView>(R.id.filmImage)
        val title = view.findViewById<TextView>(R.id.filmTitle)
        val type = view.findViewById<TextView>(R.id.filmType)
        val rating = view.findViewById<TextView>(R.id.filmRating)

        Picasso.get().load(film.imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_dialog_alert)
            .fit().centerCrop()
            .into(img)

        title.text = film.title
        type.text = film.type
        rating.text = "⭐ ${film.rating}"

        // Open details when clicking a film
        view.setOnClickListener {
            val i = Intent(context, FilmDetailsActivity::class.java)
            i.putExtra("film_id", film.id)
            i.putExtra("film_title", film.title)
            i.putExtra("film_type", film.type)
            i.putExtra("film_description", film.description)
            i.putExtra("film_rating", film.rating)
            i.putExtra("film_duration", film.duration)
            i.putExtra("film_image", film.imageUrl)
            context.startActivity(i)
        }

        return view
    }
}