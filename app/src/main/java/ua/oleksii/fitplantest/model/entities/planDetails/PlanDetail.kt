package ua.oleksii.fitplantest.model.entities.planDetails

import com.google.gson.annotations.SerializedName

class PlanDetail constructor(
    val id: String? = null,
    val name: String? = "",
    val athleteFirstName: String? = "",
    val athleteLastName: String? = "",
    val athleteImageUrl: String? = null,
    val athleteSlug: String? = null,
    val description: String? = "",
    val slug: String? = null,
    val imageLargeUrl: String? = null,
    val equipment: String?
)