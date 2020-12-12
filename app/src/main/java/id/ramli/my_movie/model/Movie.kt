package id.ramli.my_movie.model

import com.google.gson.annotations.SerializedName

/**
 * Created by ramliy10 on 10/12/20.
 */
data class Movie (
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    val id: String? = null,
    val title: String? = null,
    @SerializedName("release_date")
    val releaseDate:String? = null
)