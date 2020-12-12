package id.ramli.my_movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.ramli.my_movie.model.Movie
import id.ramli.my_movie.network.isOnline
import id.ramli.my_movie.ui.detail.DetailMovieActivity
import id.ramli.my_movie.ui.favorite.FavoriteActivity
import id.ramli.my_movie.ui.home.HomeViewModel
import id.ramli.my_movie.ui.home.NowPlayingAdapter
import id.ramli.my_movie.ui.home.PopularAdapter
import id.ramli.my_movie.ui.home.TopRatedAdapter
import id.ramli.my_movie.utils.Constans
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener  {
    var page = 1
    private lateinit var viewModel: HomeViewModel
    private val TAG = "MainActivity"
    lateinit var adapterPopular: PopularAdapter
    lateinit var adapterTopRated: TopRatedAdapter
    lateinit var adapterNowPlaing: NowPlayingAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var layoutManagerPopular: LinearLayoutManager
    lateinit var layoutNowPlaying: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(getColor(R.color.white))
        title = getString(R.string.app_name)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutManagerPopular = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutNowPlaying = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        setupRecyclerview()

        adapterPopular.listener = this
        adapterTopRated.listener = this
        adapterNowPlaing.listener = this
        getData(page)
        viewModel.getStatus().observe(this, Observer { t ->
            if (t ?: true) {
                refresh.isRefreshing = true
            } else {
                list_top_rated.visibility = View.VISIBLE
                refresh.isRefreshing = false
            }
        })

        viewModel.setPopularData().observe(this, Observer { t ->
            t?.results?.let {
                Log.d(TAG, "onCreate: ${t.results.size}")
                if (t.results.isEmpty()) {
                    Toast.makeText(this, "List Popular Empty", Toast.LENGTH_SHORT).show()

                } else
                    adapterPopular.addList(it as ArrayList<Movie>)
                    adapterPopular.notifyDataSetChanged()
            }
        })

        viewModel.setTopData().observe(this, Observer { t ->
            t?.results?.let {
                Log.d(TAG, "onCreate: ${t.results.size}")
                if (t.results.isEmpty()) {
                    Toast.makeText(this, "List Top Rated Empty", Toast.LENGTH_SHORT).show()
                } else

                    adapterTopRated.addList(it as ArrayList<Movie>)
                    adapterTopRated.notifyDataSetChanged()
            }
        })

        viewModel.setNowPlaying().observe(this, Observer { t ->
            t?.results?.let {
                Log.d(TAG, "onCreate: ${t.results.size}")
                if (t.results.isEmpty()) {
                    Toast.makeText(this, "List Now Playing Empty", Toast.LENGTH_SHORT).show()
                } else

                    adapterNowPlaing.addList(it as ArrayList<Movie>)
                adapterNowPlaing.notifyDataSetChanged()
            }
        })

        list_popular.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                Log.d(TAG, "onScrolled: $dx $dy")

                if (dx > 0) { // only when scrolling up
                    val visibleThreshold = 2
                    val lastItem = layoutManagerPopular.findLastCompletelyVisibleItemPosition()
                    val currentTotalCount = layoutManagerPopular.itemCount
                    if (currentTotalCount <= lastItem + visibleThreshold) {
                        //show your loading view
                        page++
                        if (isOnline(this@MainActivity)) {
                            viewModel.getPopularData(page)
                        } else {
                            Toast.makeText(
                                baseContext,
                                R.string.connection_failed, Toast.LENGTH_SHORT
                            ).show()
                        }
                        // load content in background
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        list_top_rated.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                Log.d(TAG, "onScrolled: $dx $dy")

                if (dx > 0) { // only when scrolling up
                    val visibleThreshold = 2
                    val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                    val currentTotalCount = layoutManager.itemCount
                    if (currentTotalCount <= lastItem + visibleThreshold) {
                        //show your loading view
                        page++
                        if (isOnline(this@MainActivity)) {
                            viewModel.getTopRatedData(page)
                        } else {
                            Toast.makeText(
                                baseContext,
                                R.string.connection_failed, Toast.LENGTH_SHORT
                            ).show()
                        }
                        // load content in background
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        list_now_playing.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                Log.d(TAG, "onScrolled: $dx $dy")

                if (dx > 0) { // only when scrolling up
                    val visibleThreshold = 2
                    val lastItem = layoutNowPlaying.findLastCompletelyVisibleItemPosition()
                    val currentTotalCount = layoutNowPlaying.itemCount
                    if (currentTotalCount <= lastItem + visibleThreshold) {
                        //show your loading view
                        page++
                        if (isOnline(this@MainActivity)) {
                            viewModel.getNowPlaying(page)
                        } else {
                            Toast.makeText(
                                baseContext,
                                R.string.connection_failed, Toast.LENGTH_SHORT
                            ).show()
                        }
                        // load content in background
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        refresh.setOnRefreshListener(this)

    }

    private fun getData(page: Int) {
        viewModel.getPopularData(page)
        viewModel.getTopRatedData(page)
        viewModel.getNowPlaying(page)
    }

    private fun setupRecyclerview() {
        list_top_rated.setHasFixedSize(true)
        list_top_rated.layoutManager = layoutManager
        adapterTopRated = TopRatedAdapter()
        list_top_rated.adapter = adapterTopRated


        list_popular.setHasFixedSize(true)
        list_popular.layoutManager = layoutManagerPopular
        adapterPopular = PopularAdapter()
        list_popular.adapter = adapterPopular

        list_now_playing.setHasFixedSize(true)
        list_now_playing.layoutManager = layoutNowPlaying
        adapterNowPlaing = NowPlayingAdapter()
        list_now_playing.adapter = adapterNowPlaing
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.action_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                return false
            }


        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFirstItemClick(view: View?, data: Any?) {
        val popular:Movie = data as Movie
        println("popularFirstItem ${popular.id}")
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(Constans.MOVIE_ID, data.id)
        startActivity(intent)

    }

    override fun onSecondItemClick(view: View?, data: Any?) {
        val popular:Movie = data as Movie
        println("popularSecondtItem ${popular.id}")
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(Constans.MOVIE_ID, data.id)
        startActivity(intent)
    }

    override fun onThirdItemClick(view: View?, data: Any?) {
        val popular:Movie = data as Movie
        println("popularThirdItem ${popular.id}")
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(Constans.MOVIE_ID, data.id)
        startActivity(intent)
    }

    override fun onRefresh() {
        getData(page)
    }
}