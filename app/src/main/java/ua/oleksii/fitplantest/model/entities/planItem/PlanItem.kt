package ua.oleksii.fitplantest.model.entities.planItem

import com.google.gson.annotations.SerializedName

class PlanItem constructor(
     val id: String? = null,
     val name: String? = null,
     val imageUrl: String? = null,
     val imageSmallUrl: String? = null,
     val athleteFirstName: String? = null,
     val athleteLastName: String? = null,
     val athleteSlug: String? = null,
     val slug: String? = null
)