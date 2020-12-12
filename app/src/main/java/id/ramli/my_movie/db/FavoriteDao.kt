package id.ramli.my_movie.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Created by ramliy10 on 11/12/20.
 */
@Dao
interface FavoriteDao {
    @Insert
    fun insert(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE _id = :id")
    fun deleteById(id: String)

    @Query("SELECT * FROM favorite")
    fun getAll() : List<Favorite>

    @Query("SELECT * FROM favorite WHERE _id = :id")
    fun getById(id: String) : List<Favorite>
}