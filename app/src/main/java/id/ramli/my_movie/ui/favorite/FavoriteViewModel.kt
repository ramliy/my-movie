package id.ramli.my_movie.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ramli.my_movie.MyApps
import id.ramli.my_movie.db.Favorite
import id.ramli.my_movie.db.MyRoomDatabase

/**
 * Created by ramliy10 on 11/12/20.
 */
class FavoriteViewModel : ViewModel() {

    private var favoriteData = MutableLiveData<List<Favorite>>()
    private val database = MyRoomDatabase.getDatabase(MyApps.applicationContext())
    private val dao = database.getFavoriteDao()
    private val listItems = arrayListOf<Favorite>()

    fun getFavoriteleData() {
        listItems.addAll(dao.getAll())
        favoriteData.value = listItems

    }

    fun deleteFavoriteData(id:String){
        dao.deleteById(id)
    }

    fun addFavoriteData(favorite: Favorite){
        if (dao.getById(favorite._id).isEmpty()) {
            dao.insert(favorite)
        }
    }

    fun setFavoriteData() : MutableLiveData<List<Favorite>> {
        return favoriteData
    }



}