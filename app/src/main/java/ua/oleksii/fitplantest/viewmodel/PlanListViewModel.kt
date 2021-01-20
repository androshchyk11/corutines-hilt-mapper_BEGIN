package ua.oleksii.fitplantest.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ua.oleksii.fitplantest.managers.ConnectionManager
import ua.oleksii.fitplantest.model.entities.planItem.PlanItemEntity
import ua.oleksii.fitplantest.model.entities.RequestError
import ua.oleksii.fitplantest.model.entities.planItem.PlanItem
import ua.oleksii.fitplantest.model.network.ApiRequestService
import ua.oleksii.fitplantest.repositories.PlanListRepository
import ua.oleksii.fitplantest.utils.DataState

class PlanListViewModel @ViewModelInject constructor( // better way to configure viewmodel injection (see another)
    val planListRepository: PlanListRepository
) : ViewModel() {

    val planItemsDataState = MutableLiveData<DataState<List<PlanItem>>>()

    init {
        getPlansList()
    }

    fun getPlansList() {

            viewModelScope.launch(Dispatchers.IO) {
                planListRepository.getPlanList().collect {
                    planItemsDataState.postValue(it)
                }




//            example for two parallel and independent requests:
//            val planListResponseDeffered = async { apiRequestService.getPlanList() }
//            val planDetailsResponseDeffered = async {apiRequestService.getPlanDetails("11") }
//            val planListResponse = planListResponseDeffered.await()
//            val responseDetails = planDetailsResponseDeffered.await()

//            example for requests chain (second request depends on response of first):
//            val response = apiRequestService.getPlanList()
//            val planDetailResponse = apiRequestService.getPlanDetails(response.body()?.planItems?.getOrNull(2)?.id ?: "")

//            simple one request:
//            val response = apiRequestService.getPlanList()

//            try {
//                val response = apiRequestService.getPlanList()
//                inProgress.postValue(false)
//
//                if (response.isSuccessful) {
//                    val planItems = response.body()?.planItems
//                    planItemsData.postValue(planItems ?: ArrayList())
//                } else {
//                    requestError.postValue(
//                        RequestError(
//                            RequestError.REQUEST_ERROR,
//                            response.code()
//                        )
//                    )
//                }
//            } catch (e: Exception) {
//                inProgress.postValue(false)
//                if (connectionDetector.isConnected.value != true) {
//                    requestError.postValue(RequestError(RequestError.CONNECTION_ERROR))
//                } else {
//                    requestError.postValue(RequestError(RequestError.REQUEST_ERROR))
//                }
//            }
//        }
//
        }

    }
}