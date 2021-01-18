package ua.oleksii.fitplantest.model.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by AlexLampa on 30.05.2018.
 */
@Parcelize
data class PlanDetailResponse constructor(
    @SerializedName("result") val planDetail: PlanDetailEntity? = null
) : Parcelable
