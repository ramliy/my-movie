package id.ramli.my_movie.model

import com.google.gson.annotations.SerializedName

/**
 * Created by ramliy10 on 10/12/20.
 */
data class MovieResponse (
    val page: Int? = null,
    val results: List<Movie>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
)