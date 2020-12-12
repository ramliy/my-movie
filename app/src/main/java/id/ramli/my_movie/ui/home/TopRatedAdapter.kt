package id.ramli.my_movie.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.ramli.my_movie.BuildConfig
import id.ramli.my_movie.R
import id.ramli.my_movie.RecyclerViewClickListener
import id.ramli.my_movie.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ramliy10 on 10/12/20.
 */
class TopRatedAdapter : RecyclerView.Adapter<TopRatedAdapter.MyHolder>(){
    private var data = ArrayList<Movie>()

    var listener: RecyclerViewClickListener? = null

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(get: Movie) {

            itemView.tv_title.text = get.title
            if (get.backdropPath!=null){
                Picasso.get().load(BuildConfig.BASE_URL_IMAGE + BuildConfig.IMAGE_PATH + get.backdropPath)
                    .into(itemView.iv_poster)
            }

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

        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int)
            = MyHolder(
        LayoutInflater.from(p0.context).inflate(R.layout.item_movie, p0, false)
    )


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: MyHolder, p1: Int) {
        p0.bindView(data.get(p1))
        p0.itemView.setOnClickListener {
            listener?.onSecondItemClick(it,data[p1])
        }

    }

    fun addList(items : ArrayList<Movie>){
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun clear(){
        data.clear()
        notifyDataSetChanged()
    }

}