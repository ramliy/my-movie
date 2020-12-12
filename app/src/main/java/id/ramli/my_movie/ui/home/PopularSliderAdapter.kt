package id.ramli.my_movie.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.islamkhsh.CardSliderAdapter
import com.squareup.picasso.Picasso
import id.ramli.my_movie.BuildConfig
import id.ramli.my_movie.R
import id.ramli.my_movie.model.Movie
import kotlinx.android.synthetic.main.item_card_content.view.*

/**
 * Created by ramliy10 on 10/12/20.
 */
class PopularSliderAdapter(private val movies: ArrayList<Movie>) :
    CardSliderAdapter<PopularSliderAdapter.PopularViewHolder>() {
    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_content, parent, false)
        return PopularViewHolder(view)
    }

    override fun bindVH(holder: PopularViewHolder, position: Int) {
        val movie = movies[position]
        holder.itemView.run {
            Picasso.get().load(BuildConfig.BASE_URL_IMAGE + BuildConfig.IMAGE_PATH + movie.backdropPath)
                .into(iv_banner)
        }

        holder.itemView.setOnClickListener { view ->
            run {
                println("itemClick ${movie.id}")
            }
        }
    }

    class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }


}