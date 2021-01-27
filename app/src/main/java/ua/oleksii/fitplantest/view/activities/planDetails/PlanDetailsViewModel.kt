package ua.oleksii.fitplantest.view.activities.planDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ua.oleksii.fitplantest.managers.ConnectionManager
import ua.oleksii.fitplantest.model.entities.planDetails.PlanDetailEntity
import ua.oleksii.fitplantest.model.entities.RequestError
import ua.oleksii.fitplantest.model.entities.planDetails.PlanDetail
import ua.oleksii.fitplantest.model.network.ApiRequestService
import ua.oleksii.fitplantest.repositories.PlanDetailsRepository
import ua.oleksii.fitplantest.utils.DataState
import javax.inject.Inject

@ActivityRetainedScoped
class PlanDetailsViewModel @Inject constructor(  // worse way to configure viewmodel injection (see another)
    var planDetailsRepository:PlanDetailsRepository
) : ViewModel() {

    val planId = MutableLiveData<String>()
    val planDetailsDataState = MutableLiveData<DataState<PlanDetail>>()


    fun getPlansDetails(id:String) {
        viewModelScope.launch(Dispatchers.IO) {
            planDetailsRepository.getPlanDetails(id).collect {
                planDetailsDataState.postValue(it)
            }
        }









//        inProgress.postValue(true)

//        viewModelScope.launch {
//            val response = apiRequestService.getPlanDetails(planId.value ?: "")
//            try {
//                inProgress.postValue(false)
//                if (response.isSuccessful) {
//                    planDetailsData.postValue(response.body()?.planDetail)
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

    }

}