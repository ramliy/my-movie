package id.ramli.my_movie.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by ramliy10 on 11/12/20.
 */
//Entity annotation to specify the table's name
@Entity(tableName = "favorite")
//Parcelable annotation to make parcelable object
@Parcelize
data class Favorite(
    //PrimaryKey annotation to declare primary key
    //ColumnInfo annotation to specify the column's name
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "backdrop_path") var backdropPath: String = "",
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "release_date") var releaseDate: String = "",
    @ColumnInfo(name = "overview") var overview: String = "",
    @ColumnInfo(name = "_id") var _id: String = ""

) : Parcelable {
}