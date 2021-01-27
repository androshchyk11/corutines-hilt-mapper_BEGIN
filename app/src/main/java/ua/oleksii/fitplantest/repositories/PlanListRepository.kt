package ua.oleksii.fitplantest.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import ua.oleksii.fitplantest.model.entities.planDetails.PlanDetail
import ua.oleksii.fitplantest.model.entities.planItem.PlanItem
import ua.oleksii.fitplantest.model.network.ApiRequestService
import ua.oleksii.fitplantest.utils.DataState
import ua.oleksii.fitplantest.utils.mappers.PlanDetailsMapper
import ua.oleksii.fitplantest.utils.mappers.PlanListMapper
import java.lang.Exception
import javax.inject.Inject

class PlanListRepository @Inject constructor(
    private val apiRequestService: ApiRequestService,
    private val planListMapper: PlanListMapper,
    private val planDetailsMapper: PlanDetailsMapper
) {



    suspend fun getPlanList(): Flow<DataState<List<PlanItem>>> = flow {
        emit(DataState.Loading(true))

        try {
            val response = apiRequestService.getPlanList()

            emit(DataState.Loading(false))

            if (response.isSuccessful) {
                response.body()?.planItems?.let { list ->
                    emit(DataState.Success(planListMapper.mapFromEntityList(list)))
                }
            } else {
                emit(DataState.Error(Exception()))
            }

        } catch (e: Exception) {
            emit(DataState.Loading(false))
            emit(DataState.Error(e))
        }
    }


    suspend fun getPlanDetails(id:String? = "23"): Flow<DataState<PlanDetail>> = flow {
        emit(DataState.Loading(true))

        try {
            val response = apiRequestService.getPlanDetails(id?: "23")


            emit(DataState.Loading(false))

            if (response.isSuccessful) {
                response.body()?.planDetail?.let { it ->
                    emit(DataState.Success(planDetailsMapper.responseEntityToUiEntity(it)))
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