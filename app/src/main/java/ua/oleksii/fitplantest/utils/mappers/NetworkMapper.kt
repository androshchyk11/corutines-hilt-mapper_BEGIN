package ua.oleksii.fitplantest.utils.mappers

import ua.oleksii.fitplantest.model.entities.login.Login
import ua.oleksii.fitplantest.model.entities.login.LoginResponse
import javax.inject.Inject

class NetworkMapper @Inject constructor():LoginMapper<LoginResponse?, Login> {


    override fun loginResponseToLoginEntity(domainModel: LoginResponse?): Login {
        domainModel?.accessToken.let{
            return Login(authToken = it.toString())
        }
    }
}