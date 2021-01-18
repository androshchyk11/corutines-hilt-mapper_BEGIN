package ua.oleksii.fitplantest.utils

import android.util.Log

/**
 * Created by AlexLampa on 30.05.2018.
 */
class FitAppLogger {

    companion object {
        fun showLog(text: String?) {
            Log.d("FitAppLogger", "$text")
        }
    }
}
