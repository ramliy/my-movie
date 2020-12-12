package id.ramli.my_movie

import android.view.View

/**
 * Created by ramliy10 on 10/12/20.
 */
interface RecyclerViewClickListener {
    fun onFirstItemClick(view: View?, data: Any?)
    fun onSecondItemClick(view: View?, data: Any?)
    fun onThirdItemClick(view: View?, data: Any?)
}