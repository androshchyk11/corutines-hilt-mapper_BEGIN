package ua.oleksii.fitplantest.model.entities.planItem

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by AlexLampa on 30.05.2018.
 */
@Parcelize
data class PlanListResponse constructor(
    @SerializedName("result") val planItems: List<PlanItemEntity>? = null
) : Parcelable
