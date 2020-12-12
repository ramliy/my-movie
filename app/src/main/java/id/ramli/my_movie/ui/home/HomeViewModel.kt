package id.ramli.my_movie.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ramli.my_movie.BuildConfig
import id.ramli.my_movie.model.DetailMovieResponse
import id.ramli.my_movie.model.MovieResponse
import id.ramli.my_movie.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by ramliy10 on 10/12/20.
 */

class HomeViewModel : ViewModel() {
    private var status = MutableLiveData<Boolean>()
    private var popularData = MutableLiveData<MovieResponse>()
    private var topRatedData = MutableLiveData<MovieResponse>()
    private var nowPlayingData = MutableLiveData<MovieResponse>()
    private var detailMovie = MutableLiveData<DetailMovieResponse>()

    fun getPopularData(page: Int) {
        status.value = true

        NetworkConfig().api().popular(BuildConfig.API_KEY, page).enqueue(object :
            Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                status.value = true
                popularData.value = null
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                status.value = false

                if(response.isSuccessful){
                    popularData.value = response.body()
                }
                else{
                    status.value = true
                }

            }
        })

    }

    fun getTopRatedData(page: Int) {
        status.value = true

        NetworkConfig().api().topRated(BuildConfig.API_KEY, page).enqueue(object :
            Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                status.value = true
                topRatedData.value = null
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                status.value = false

                if(response.isSuccessful){
                    topRatedData.value = response.body()
                }
                else{
                    status.value = true
                }

            }
        })

    }

    fun getNowPlaying(page: Int) {
        status.value = true

        NetworkConfig().api().nowPlaying(BuildConfig.API_KEY, page).enqueue(object :
            Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                status.value = true
                nowPlayingData.value = null
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                status.value = false

                if(response.isSuccessful){
                    nowPlayingData.value = response.body()
                }
                else{
                    status.value = true
                }

            }
        })

    }

    fun getDetailMovie(movieId: Int) {
        status.value = true

        NetworkConfig().api().detail(movieId,BuildConfig.API_KEY).enqueue(object :
            Callback<DetailMovieResponse> {
            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                status.value = true
                detailMovie.value = null
            }

            override fun onResponse(call: Call<DetailMovieResponse>, response: Response<DetailMovieResponse>) {
                status.value = false

                if(response.isSuccessful){
                    detailMovie.value = response.body()
                }
                else{
                    status.value = true
                }

            }
        })

    }

    fun setPopularData() : MutableLiveData<MovieResponse> {
        return popularData
    }

    fun setTopData() : MutableLiveData<MovieResponse> {
        return topRatedData
    }

    fun setNowPlaying() : MutableLiveData<MovieResponse> {
        return nowPlayingData
    }

    fun setMoviewDetail() : MutableLiveData<DetailMovieResponse>{
        return detailMovie
    }

    fun getStatus():MutableLiveData<Boolean>{
        status.value = true
        return status
    }

}