package id.ramli.my_movie.model

import com.google.gson.annotations.SerializedName

/**
 * Created by ramliy10 on 11/12/20.
 */
data class DetailMovieResponse (
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    val overview: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val tagline: String? = null,
    val id: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null

)