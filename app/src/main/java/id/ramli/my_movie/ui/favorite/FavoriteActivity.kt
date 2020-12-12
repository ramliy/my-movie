package id.ramli.my_movie.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.ramli.my_movie.R
import id.ramli.my_movie.db.Favorite
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_favorite.toolbar
import kotlinx.android.synthetic.main.activity_main.*

class FavoriteActivity : AppCompatActivity() {
    private lateinit var viewModel: FavoriteViewModel
    lateinit var adapter: FavoriteAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        setSupportActionBar(toolbar)
        title = getString(R.string.favorite)
        toolbar.setTitleTextColor(getColor(R.color.white))
        viewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)

        layoutManager = LinearLayoutManager(this)

        setupRecyclerview()
        getData()

        viewModel.setFavoriteData().observe(this, Observer { t ->
            t?.let {
                if (it.isEmpty()) {
                    Toast.makeText(this, "List Favorite Empty", Toast.LENGTH_SHORT).show()
                } else
                    adapter.addList(it as ArrayList<Favorite>)
                adapter.notifyDataSetChanged()

            }
        })
    }

    private fun getData() {
        viewModel.getFavoriteleData()

    }

    private fun setupRecyclerview() {
        list.setHasFixedSize(true)
        list.layoutManager = layoutManager
        adapter = FavoriteAdapter()
        list.adapter = adapter
    }
}
