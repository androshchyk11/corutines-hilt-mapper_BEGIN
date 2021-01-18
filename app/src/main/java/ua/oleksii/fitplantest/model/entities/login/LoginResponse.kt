package ua.oleksii.fitplantest.model.entities.login

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by AlexLampa on 30.05.2018.
 */

@Parcelize
data class LoginResponse constructor(
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("token_type") val tokenType: String?,
    @SerializedName("scope") val scope: String?,
    @SerializedName("jti") val jti: String?
) : Parcelable
