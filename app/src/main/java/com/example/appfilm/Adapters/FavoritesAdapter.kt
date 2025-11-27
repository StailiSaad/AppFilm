package com.example.appfilm.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appfilm.Models.Film
import com.example.appfilm.R
import com.squareup.picasso.Picasso

/**
 * RecyclerView adapter for displaying a list of favorite films.
 * Handles item click and long-click actions for interactions.
 */
class FavoritesAdapter(
    private var films: List<Film>,
    private val onItemClick: (Film) -> Unit,
    private val onItemLongClick: (Film, Int) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FilmViewHolder>() {

    /**
     * ViewHolder class for film items in the RecyclerView
     */
    inner class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val filmImage: ImageView = itemView.findViewById(R.id.filmImage)
        val filmTitle: TextView = itemView.findViewById(R.id.filmTitle)
        val filmType: TextView = itemView.findViewById(R.id.filmType)
        val filmRating: TextView = itemView.findViewById(R.id.filmRating)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick(films[adapterPosition])
                }
            }

            itemView.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemLongClick(films[adapterPosition], adapterPosition)
                    true
                } else {
                    false
                }
            }
        }
    }

    /**
     * Creates new ViewHolder instances when needed
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film, parent, false)
        return FilmViewHolder(view)
    }

    /**
     * Binds film data to the ViewHolder at the specified position
     */
    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = films[position]

        Picasso.get().load(film.imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_dialog_alert)
            .fit().centerCrop()
            .into(holder.filmImage)

        holder.filmTitle.text = film.title
        holder.filmType.text = film.type
        holder.filmRating.text = "⭐ ${film.rating}"
    }

    /**
     * Returns the total number of films in the data set
     */
    override fun getItemCount(): Int = films.size

    /**
     * Updates the adapter with a new list of films
     */
    fun updateFilms(newFilms: List<Film>) {
        films = newFilms
        notifyDataSetChanged()
    }
}