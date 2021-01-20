package ua.oleksii.fitplantest.repositories

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ua.oleksii.fitplantest.model.entities.planDetails.PlanDetail
import ua.oleksii.fitplantest.model.entities.planItem.PlanItem
import ua.oleksii.fitplantest.model.network.ApiRequestService
import ua.oleksii.fitplantest.utils.DataState
import ua.oleksii.fitplantest.utils.mappers.NetworkMapper
import ua.oleksii.fitplantest.utils.mappers.PlanDetailsMapper
import ua.oleksii.fitplantest.utils.mappers.PlanListMapper
import java.lang.Exception
import javax.inject.Inject

@Module
@InstallIn(ActivityRetainedComponent::class)
class PlanDetailsRepository @Inject constructor(
    private val apiRequestService: ApiRequestService,
    private val planListMapper: PlanDetailsMapper
) {


    suspend fun getPlanDetails(id:String? = "23"): Flow<DataState<PlanDetail>> = flow {
        emit(DataState.Loading(true))

        try {
            val response = apiRequestService.getPlanDetails(id?: "")

            emit(DataState.Loading(false))

            if (response.isSuccessful) {
                response.body()?.planDetail?.let { it ->
                    emit(DataState.Success(planListMapper.responseEntityToUiEntity(it)))
                }
            } else {
                emit(DataState.Error(Exception()))
            }

        } catch (e: Exception) {
            emit(DataState.Loading(false))
            emit(DataState.Error(e))
        }
    }

}