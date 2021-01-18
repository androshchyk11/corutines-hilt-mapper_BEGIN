package ua.oleksii.fitplantest.managers

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesManager @Inject constructor(@ApplicationContext  context: Context) {

    private val PREFERENCES_FILE_NAME = "fitplantest.spref"
    private val USER_TOKEN = "USER_TOKEN"
    private val IS_WITH_IMAGES = "IS_WITH_IMAGES"
    private val sharedPreferences: SharedPreferences

    init {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    }

    var userAccessToken: String?
        get() = sharedPreferences.getString(USER_TOKEN, null)
        set(token) {
            sharedPreferences.edit {
                putString(USER_TOKEN, token)
            }
        }


    var isWithImages: Boolean
        get() = sharedPreferences.getBoolean(IS_WITH_IMAGES, false)
        set(value) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(IS_WITH_IMAGES, value)
            editor.apply()
        }

    fun clearAllSharedPreferences() {
        sharedPreferences.edit()
            .clear()
            .apply()
    }

}