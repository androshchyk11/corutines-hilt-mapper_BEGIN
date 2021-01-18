package ua.oleksii.fitplantest.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ua.oleksii.fitplantest.model.entities.login.LoginResponse
import ua.oleksii.fitplantest.model.entities.RequestError
import ua.oleksii.fitplantest.model.entities.login.Login
import ua.oleksii.fitplantest.repositories.LoginRepository

class LoginViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {


    var email = MutableLiveData<String>("test@fitplanapp.com")
    var password = MutableLiveData<String>("fitplan123")

    var inProgress = MutableLiveData<Boolean>()
    val requestError = MutableLiveData<RequestError>()
    val successfullyAuthorised = MutableLiveData<Login>()


    fun authUser() {
        viewModelScope.launch {
             successfullyAuthorised.value =
                loginRepository.authUser()
        }


//        if (connectionDetector.isConnected.value != true) {
//            requestError.postValue(RequestError(RequestError.CONNECTION_ERROR))
//            return
//        }
//        inProgress.postValue(true)
//
//        viewModelScope.launch {
//            //   delay(5000L)
////            val response = apiRequestService.login(clientId, clientSecret, grantType, email.value ?: "", password.value ?: "")
//            try {
//                inProgress.postValue(false)
//
//                val accessToken = response.body()?.accessToken
//                if (accessToken.isNullOrEmpty()) {
//                    requestError.postValue(
//                        RequestError(
//                            RequestError.WRONG_CREDENTIALS_ERROR,
//                            response.code()
//                        )
//                    )
//                } else {
//                    sharedPreferencesManager.userAccessToken = accessToken
//                    successfullyAuthorised.postValue(true)
//                }
//            } catch (e: Exception) {
//                inProgress.postValue(false)
//                if (connectionDetector.isConnected.value != true) {
//                    requestError.postValue(RequestError(RequestError.CONNECTION_ERROR))
//                } else {
//                    requestError.postValue(RequestError(RequestError.REQUEST_ERROR))
//                }
//            }
//
//        }

    }

}