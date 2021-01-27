package ua.oleksii.fitplantest.utils.mappers

import ua.oleksii.fitplantest.model.entities.planDetails.PlanDetail
import ua.oleksii.fitplantest.model.entities.planDetails.PlanDetailEntity
import ua.oleksii.fitplantest.utils.Mapper
import javax.inject.Inject

class PlanDetailsMapper @Inject constructor() : Mapper<PlanDetailEntity?, PlanDetail> {


    override fun responseEntityToUiEntity(domainModel: PlanDetailEntity?): PlanDetail {
        return PlanDetail(
            id = domainModel?.id,
            name = domainModel?.name,
            athleteFirstName = domainModel?.athleteFirstName,
            athleteLastName = domainModel?.athleteLastName,
            athleteImageUrl = domainModel?.athleteImageUrl,
            athleteSlug = domainModel?.athleteSlug,
            description = domainModel?.description,
            slug = domainModel?.slug,
            imageLargeUrl = domainModel?.imageLargeUrl,
            equipment = domainModel?.equipment
        )
    }
}