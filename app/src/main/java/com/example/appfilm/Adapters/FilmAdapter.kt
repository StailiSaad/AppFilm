package com.example.tpl0part2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.example.tpl0part2.Models.Film
import com.example.tpl0part2.databinding.ItemFilmBinding

/**
 * Adapter for displaying films in a RecyclerView.
 * 
 * This adapter handles the display of film items with proper view recycling
 * and click event handling. It includes fade-in animations for items.
 * 
 * @property films List of films to display
 * @property listener Click listener for film items
 */
class FilmAdapter(
    private val films: List<Film>,
    private val listener: OnFilmClickListener
) : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    /**
     * Interface for handling film item click events.
     */
    interface OnFilmClickListener {
        /**
         * Called when a film item is clicked.
         * 
         * @param film The film that was clicked
         */
        fun onFilmClick(film: Film)
    }

    /**
     * ViewHolder class for film items.
     * 
     * @property binding View binding for the film item layout
     */
    class FilmViewHolder(val binding: ItemFilmBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Called when RecyclerView needs a new ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = ItemFilmBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FilmViewHolder(binding)
    }

    /**
     * Called to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = films[position]
        
        holder.binding.apply {
            filmTitle.text = film.title
            filmType.text = film.getTypeString()
            filmRating.text = "⭐ ${film.getRatingString()}"
            
            // Set click listener
            root.setOnClickListener {
                listener.onFilmClick(film)
            }
            
            // Add fade-in animation
            setFadeAnimation(root)
        }
    }

    /**
     * Returns the total number of items in the data set.
     */
    override fun getItemCount(): Int = films.size

    /**
     * Applies a fade-in animation to the view.
     * 
     * @param view The view to animate
     */
    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        view.startAnimation(anim)
    }
}
