package com.test.nmla.tdnewmovieslistapp.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.nmla.tdnewmovieslistapp.R
import com.test.nmla.tdnewmovieslistapp.models.Movie

class MovieAdapter(movies: java.util.ArrayList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movies: ArrayList<Movie> = movies

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val itemsList = movies[position]

        val title: String? = itemsList.title
        val year: String? = itemsList.year
        val posterUrl: String? = itemsList.posterUrl

        holder.title.text = title
        holder.year.text = year

        Picasso.get().load(posterUrl).fit().centerInside().into(holder.poster)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster: ImageView = itemView.findViewById(R.id.poster)
        val title: TextView = itemView.findViewById(R.id.title)
        val year: TextView = itemView.findViewById(R.id.year)
    }
}