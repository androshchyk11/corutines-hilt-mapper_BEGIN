package ua.oleksii.fitplantest.repositories

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ua.oleksii.fitplantest.model.entities.login.Login
import ua.oleksii.fitplantest.model.network.ApiRequestService
import ua.oleksii.fitplantest.utils.DataState
import ua.oleksii.fitplantest.utils.mappers.LoginMapper
import java.lang.Exception
import javax.inject.Inject


@Module
@InstallIn(ActivityRetainedComponent::class)
class LoginRepository @Inject constructor(
    private val apiRequestService: ApiRequestService,
    private val loginMapper: LoginMapper
) {
    private val clientId = "XW9LtUlJfcCHMJbLyLen3lglY4COUgmCQErwjze7"
    private val clientSecret =
        "ae55LjKyfVAf9dWaUX9HwoU5tpwHAVn2jKh8Of9zu3TP4zlD7JwguJhDYxXRT9zR2iuOIfHLrNiOAQSyAfRFs6dI7uXE8Yg7l3yyw7NTABnLr94VuPFKUOaaaCZ7xAv3"
    private val grantType = "password"


    // UserName: test@fitplanapp.com
    // Password: fitplan123

    suspend fun authUser(email:String,password:String): Flow<DataState<Login>> = flow {
        emit(DataState.Loading(true))
        try {
            val response = apiRequestService
                .login(clientId, clientSecret, grantType, email , password)

            emit(DataState.Loading(false))

            if(response.isSuccessful){
                emit(DataState.Success(loginMapper.loginResponseToLoginEntity(response.body())))
            }else{
                emit(DataState.Error(Exception()))
            }

        } catch (e: Exception) {
            emit(DataState.Loading(false))
            emit(DataState.Error(e))
        }
    }
}