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
import kotlinx.android.synthetic.main.item_card_content.view.*

/**
 * Created by ramliy10 on 10/12/20.
 */
class PopularAdapter : RecyclerView.Adapter<PopularAdapter.MyHolder>(){
    private var data = ArrayList<Movie>()

    var listener: RecyclerViewClickListener? = null

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(get: Movie) {

//            itemView.tv_title.text = get.title
            if (get.backdropPath!=null){
                Picasso.get().load(BuildConfig.BASE_URL_IMAGE + BuildConfig.IMAGE_PATH + get.backdropPath)
                    .into(itemView.iv_banner)
            }

            /*itemView.tv_title.text = get.headline?.newsTitle
            itemView.tv_snippet.text = get.snippet


            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val output = SimpleDateFormat("dd/MM/yyyy HH:mm")

            var d: Date? = null
            try {
                d = input.parse(get.pubDate?.subSequence(0, (get.pubDate?.length ?: 5) -5) as String)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val formatted: String = output.format(d)

            itemView.tv_date.text = formatted
            var listMultimedia : List<Multimedia>? = get.multimedia
            if (listMultimedia != null) {
                if (listMultimedia.size>0)
                    Picasso.get().load(Constans.BASE_URL + get.multimedia?.get(0)?.url)
                        .into(itemView.iv_image)

            }*/

        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int)
            = MyHolder(
        LayoutInflater.from(p0.context).inflate(R.layout.item_card_content, p0, false)
    )


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: MyHolder, p1: Int) {
        p0.bindView(data.get(p1))
        p0.itemView.setOnClickListener {
            listener?.onFirstItemClick(it,data[p1])
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