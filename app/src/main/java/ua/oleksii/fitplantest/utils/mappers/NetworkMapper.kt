package ua.oleksii.fitplantest.utils.mappers

import ua.oleksii.fitplantest.model.entities.login.Login
import ua.oleksii.fitplantest.model.entities.login.LoginResponse
import ua.oleksii.fitplantest.utils.mappers.abstraction.Mapper
import javax.inject.Inject

class NetworkMapper @Inject constructor(): Mapper<LoginResponse?, Login> {


    override fun responseEntityToUiEntity(domainModel: LoginResponse?): Login {
        domainModel?.accessToken.let{
            return Login(authToken = it.toString())
        }
    }
}