package id.ramli.my_movie

import androidx.multidex.MultiDexApplication

/**
 * Created by ramliy10 on 10/12/20.
 */
class MyApps : MultiDexApplication() {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApps? = null

        fun applicationContext() : MyApps {
            return instance as MyApps!!
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}