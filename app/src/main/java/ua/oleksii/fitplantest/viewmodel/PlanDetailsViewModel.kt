package ua.oleksii.fitplantest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.launch
import ua.oleksii.fitplantest.managers.ConnectionManager
import ua.oleksii.fitplantest.model.entities.PlanDetailEntity
import ua.oleksii.fitplantest.model.entities.RequestError
import ua.oleksii.fitplantest.model.network.ApiRequestService
import javax.inject.Inject

@ActivityRetainedScoped
class PlanDetailsViewModel @Inject constructor(  // worse way to configure viewmodel injection (see another)
    var apiRequestService: ApiRequestService,
    var connectionDetector: ConnectionManager
) : ViewModel() {

    val planId = MutableLiveData<String>()
    val inProgress = MutableLiveData<Boolean>()
    val requestError = MutableLiveData<RequestError>()
    val planDetailsData = MutableLiveData<PlanDetailEntity>()

    fun getPlansDetails() {
        inProgress.postValue(true)

        viewModelScope.launch {
            val response = apiRequestService.getPlanDetails(planId.value ?: "")
            try {
                inProgress.postValue(false)
                if (response.isSuccessful) {
                    planDetailsData.postValue(response.body()?.planDetail)
                } else {
                    requestError.postValue(
                        RequestError(
                            RequestError.REQUEST_ERROR,
                            response.code()
                        )
                    )
                }
            } catch (e: Exception) {
                inProgress.postValue(false)
                if (connectionDetector.isConnected.value != true) {
                    requestError.postValue(RequestError(RequestError.CONNECTION_ERROR))
                } else {
                    requestError.postValue(RequestError(RequestError.REQUEST_ERROR))
                }
            }
        }

    }

}