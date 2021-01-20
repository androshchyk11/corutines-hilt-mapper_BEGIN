package ua.oleksii.fitplantest.utils.mappers

import com.google.gson.annotations.SerializedName
import ua.oleksii.fitplantest.model.entities.login.Login
import ua.oleksii.fitplantest.model.entities.login.LoginResponse
import ua.oleksii.fitplantest.model.entities.planItem.PlanItem
import ua.oleksii.fitplantest.model.entities.planItem.PlanItemEntity
import ua.oleksii.fitplantest.utils.mappers.abstraction.Mapper
import javax.inject.Inject

class PlanListMapper @Inject constructor() : Mapper<PlanItemEntity?, PlanItem> {

    override fun mapFromEntityList(entities: List<PlanItemEntity?>): List<PlanItem> {
        return super.mapFromEntityList(entities)
    }

    override fun responseEntityToUiEntity(domainModel: PlanItemEntity?):PlanItem {
        return PlanItem(
            id = domainModel?.id,
            name = domainModel?.name,
            imageUrl = domainModel?.imageUrl,
            imageSmallUrl = domainModel?.imageSmallUrl,
            athleteFirstName = domainModel?.athleteFirstName,
            athleteLastName = domainModel?.athleteLastName,
            athleteSlug = domainModel?.athleteSlug,
            slug = domainModel?.slug
        )
    }
}