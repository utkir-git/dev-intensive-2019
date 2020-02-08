package ru.skillbranch.devintensive.repositories

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.models.Profile

object PreferencesRepository {
   private const val FIRST_NAME = "FIRST_NAME"
   private const val LAST_NAME = "LAST_NAME"
   private const val ABOUT = "ABOUT"
   private const val REPOSTORY = "REPOSTORY"
   private const val RATING = "RATING"
   private const val RESPECT = "RESPECT"
   private const val APPTHEME = "APPTHEME"
    private val prefs:SharedPreferences by lazy{
        val ctx = App.applicationContext()
        PreferenceManager.getDefaultSharedPreferences(ctx)
    }
    fun getProfile(): Profile?= Profile(

       prefs.getString(FIRST_NAME, "")!!,
       prefs.getString(LAST_NAME, "")!!,
       prefs.getString(ABOUT, "")!!,
       prefs.getString(REPOSTORY, "")!!,
       prefs.getInt(RATING, 0)!!,
       prefs.getInt(RESPECT, 0)!!

    )

    fun saveProfile(profile: Profile) {
        with(profile) {
            putvalue(FIRST_NAME to firstName)
            putvalue(LAST_NAME to lastName)
            putvalue(ABOUT to about)
            putvalue(REPOSTORY to repository)
            putvalue(RATING to rating)
            putvalue(RESPECT to respect)
        }
    }

    private fun putvalue(pair: Pair<String,Any>) = with(prefs.edit()){
        val key = pair.first
        val value = pair.second
        when(value){
            is String -> putString(key,value)
            is Int -> putInt(key,value)
            is Boolean -> putBoolean (key,value)
            is Long -> putLong (key,value)
            is Float -> putFloat (key,value)
            else -> error("Only primitives types can be stored in Shared Preferences")
        }
        apply()

    }

    fun saveAppTheme(theme: Int) {
putvalue(APPTHEME to theme)
    }
    fun getAppTheme():Int= prefs.getInt(APPTHEME,AppCompatDelegate.MODE_NIGHT_NO)


}
