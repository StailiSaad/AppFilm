package com.example.appfilm.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appfilm.Models.Film
import com.example.appfilm.R

/**
 * RecyclerView Adapter for displaying films.
 * Supports filtering and item animations.
 */
class FilmAdapter(
    private var filmList: MutableList<Film>,
    private val onItemClick: (Film) -> Unit
) : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    private var fullList: List<Film> = ArrayList(filmList)

    /**
     * ViewHolder for film item.
     */
    inner class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(R.id.filmTitle)
        private val imageView: ImageView = itemView.findViewById(R.id.filmImage)

        fun bind(film: Film) {
            titleText.text = film.title
            imageView.setImageResource(film.imageResId)

            itemView.setOnClickListener {
                onItemClick(film)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film, parent, false)
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {

        // Animation
        holder.itemView.alpha = 0f
        holder.itemView.animate()
            .alpha(1f)
            .setDuration(400)
            .start()

        holder.bind(filmList[position])
    }

    override fun getItemCount(): Int = filmList.size

    /**
     * Filters films by title.
     */
    fun filter(query: String) {
        filmList.clear()
        if (query.isEmpty()) {
            filmList.addAll(fullList)
        } else {
            filmList.addAll(
                fullList.filter {
                    it.title.contains(query, ignoreCase = true)
                }
            )
        }
        notifyDataSetChanged()
    }
}
