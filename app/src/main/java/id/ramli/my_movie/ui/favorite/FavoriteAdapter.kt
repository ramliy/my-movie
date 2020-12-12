package id.ramli.my_movie.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.ramli.my_movie.BuildConfig
import id.ramli.my_movie.R
import id.ramli.my_movie.db.Favorite
import kotlinx.android.synthetic.main.item_favorite.view.*
import kotlinx.android.synthetic.main.item_movie.view.tv_release_date
import kotlinx.android.synthetic.main.item_movie.view.tv_title
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ramliy10 on 11/12/20.
 */
class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.MyHolder>(){
    private var data = ArrayList<Favorite>()
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(get: Favorite) {

            itemView.tv_title.text = get.title
            val input = SimpleDateFormat("yyyy-MM-dd")
            val output = SimpleDateFormat("dd/MM/yyyy")

            var d: Date? = null
            try {
                d = input.parse(get.releaseDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val formatted: String = output.format(d)
            itemView.tv_release_date.text = formatted
            if (get.backdropPath != null) {
                Picasso.get()
                    .load(BuildConfig.BASE_URL_IMAGE + BuildConfig.IMAGE_PATH + get.backdropPath)
                    .into(itemView.iv_poster_favorite)
            }
            itemView.tv_review.text = get.overview
        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int)
            = MyHolder(
        LayoutInflater.from(p0.context).inflate(R.layout.item_favorite, p0, false)
    )


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: MyHolder, p1: Int) {
        p0.bindView(data.get(p1))
    }

    fun addList(items : ArrayList<Favorite>){
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun clear(){
        data.clear()
        notifyDataSetChanged()
    }

}