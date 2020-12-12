package id.ramli.my_movie.network

import id.ramli.my_movie.model.DetailMovieResponse
import id.ramli.my_movie.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by ramliy10 on 10/12/20.
 */
interface ApiService {
    @GET("3/movie/popular")
    fun popular(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("3/movie/top_rated")
    fun topRated(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("3/movie/now_playing")
    fun nowPlaying(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("3/movie/{movie_id}")
    fun detail(
        @Path("movie_id") id:Int,
        @Query("api_key") api_key: String
    ): Call<DetailMovieResponse>
}