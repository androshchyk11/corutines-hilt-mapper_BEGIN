package ua.oleksii.fitplantest.model.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by AlexLampa on 30.05.2018.
 */

@Parcelize
data class PlanItemEntity constructor(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("imageUrl") val imageUrl: String? = null,
    @SerializedName("imageSmallUrl") val imageSmallUrl: String? = null,
    @SerializedName("athleteFirstName") val athleteFirstName: String? = null,
    @SerializedName("athleteLastName") val athleteLastName: String? = null,
    @SerializedName("athleteSlug") val athleteSlug: String? = null,
    @SerializedName("slug") val slug: String? = null
) : Parcelable
