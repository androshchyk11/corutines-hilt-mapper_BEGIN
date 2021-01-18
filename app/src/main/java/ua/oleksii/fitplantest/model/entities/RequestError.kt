package ua.oleksii.fitplantest.model.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by AlexLampa on 13.08.2019.
 */
@Parcelize
class RequestError(
    var type: String,
    var code: Int? = null
) : Parcelable {
    companion object {
        const val CONNECTION_ERROR = "CONNECTION_ERROR"
        const val REQUEST_ERROR = "REQUEST_ERROR"
        const val WRONG_CREDENTIALS_ERROR = "WRONG_CREDENTIALS_ERROR"
    }
}