package ua.oleksii.fitplantest.model.entities.planDetails

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by AlexLampa on 30.05.2018.
 */

@Parcelize
data class PlanDetailEntity constructor(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = "",
    @SerializedName("athleteFirstName") val athleteFirstName: String? = "",
    @SerializedName("athleteLastName") val athleteLastName: String? = "",
    @SerializedName("athleteImageUrl") val athleteImageUrl: String? = null,
    @SerializedName("athleteSlug") val athleteSlug: String? = null,
    @SerializedName("description") val description: String? = "",
    @SerializedName("slug") val slug: String? = null,
    @SerializedName("imageLargeUrl") val imageLargeUrl: String? = null,
    @SerializedName("equipment") val equipment: String?
) : Parcelable
