package com.test.nmla.tdnewmovieslistapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.test.nmla.tdnewmovieslistapp.data.MovieAdapter
import com.test.nmla.tdnewmovieslistapp.databinding.ActivityMainBinding
import com.test.nmla.tdnewmovieslistapp.models.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movies: ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movies = java.util.ArrayList()

        CoroutineScope(Dispatchers.IO).launch {
            getMovies()
        }
    }

    private fun getMovies() {
//        val url = "https://www.themoviedb.org/?apikey=d866f943f2d70dee6fcd311d094d5720"
        val url = "http://www.omdbapi.com/?apikey=c8e3e312&s=superman"
        val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET,
            url, null, { response ->
                try {
                    val jsonArray = response.getJSONArray("Search")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val title = jsonObject.getString("Title")
                        val year = jsonObject.getString("Year")
                        val posterUrl = jsonObject.getString("Poster")
                        val movie = Movie()
                        movie.title = title
                        movie.year = year
                        movie.posterUrl = posterUrl
                        movies.add(movie)
                    }
                    binding.recyclerView.hasFixedSize()
                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                    binding.recyclerView.adapter = MovieAdapter(movies)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { error ->
            error.printStackTrace()
        }

        queue.add(request)
    }
}