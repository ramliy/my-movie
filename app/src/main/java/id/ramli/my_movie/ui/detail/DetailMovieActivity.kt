package id.ramli.my_movie.ui.detail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import id.ramli.my_movie.BuildConfig
import id.ramli.my_movie.R
import id.ramli.my_movie.db.Favorite
import id.ramli.my_movie.db.FavoriteDao
import id.ramli.my_movie.db.MyRoomDatabase
import id.ramli.my_movie.model.DetailMovieResponse
import id.ramli.my_movie.ui.favorite.FavoriteViewModel
import id.ramli.my_movie.ui.home.HomeViewModel
import id.ramli.my_movie.utils.Constans
import kotlinx.android.synthetic.main.activity_detail_movie.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private val TAG = "DetailMovieActivity"

    private lateinit var database: MyRoomDatabase
    private lateinit var dao: FavoriteDao
    var isFavoritAdded: Boolean = false
    private lateinit var detailMovie: DetailMovieResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        database = MyRoomDatabase.getDatabase(applicationContext)
        dao = database.getFavoriteDao()

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(getColor(R.color.white))
        title = getString(R.string.detail)
        var movieId = intent.getStringExtra(Constans.MOVIE_ID)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        Log.d(TAG, "onCreate: $movieId")

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        if (movieId != null) {
            getData(movieId.toInt())
        }

        viewModel.setMoviewDetail().observe(this, Observer { t ->
            t?.let {
                detailMovie = t
                tv_title.text = t.originalTitle
                val input = SimpleDateFormat("yyyy-MM-dd")
                val output = SimpleDateFormat("dd/MM/yyyy")

                var d: Date? = null
                try {
                    d = input.parse(t.releaseDate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val formatted: String = output.format(d)

                tv_release_date.text = formatted

                tv_review.text = t.overview
                if (!t.tagline.equals("")) {
                    tv_description.text = t.tagline
                } else tv_description.text = "-"

                if (t.backdropPath != null) {
                    Picasso.get()
                        .load(BuildConfig.BASE_URL_IMAGE + BuildConfig.IMAGE_PATH + t.backdropPath)
                        .into(iv_banner)
                }


            }
        })


        if (dao.getById(movieId.toString()).isNotEmpty()){
            bottomAppBar.getMenu().getItem(1).setIcon(R.drawable.ic_favorite_red)
            isFavoritAdded = true
        }

        bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_share -> {
                    // Handle search icon press
                    Log.d(TAG, "onCreate: Share")
                    true
                }
                R.id.action_like -> {
                    Log.d(TAG, "onCreate: like")
                    // Handle more item (inside overflow menu) press
                    if (!isFavoritAdded) {
                        menuItem.setIcon(R.drawable.ic_favorite_red)
                        addFavorite(Favorite(0,detailMovie.posterPath!!, detailMovie.originalTitle!!,detailMovie.releaseDate!!,detailMovie.overview!!,movieId!!))
                        isFavoritAdded = true
                    } else {
                        menuItem.setIcon(R.drawable.ic_favorite_grey)
                        isFavoritAdded = false
                        deleteFavorite(movieId!!)
                    }


                    true
                }
                else -> false
            }
        }

    }

    private fun addFavorite(favorite: Favorite) {
        favoriteViewModel.addFavoriteData(favorite)
        Toast.makeText(applicationContext, "Favorite added", Toast.LENGTH_SHORT).show()
    }

    private fun deleteFavorite(id: String) {
        favoriteViewModel.deleteFavoriteData(id)
        Toast.makeText(applicationContext, "Favorite removed", Toast.LENGTH_SHORT).show()
    }

    private fun getData(movieId: Int) {
        viewModel.getDetailMovie(movieId)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}