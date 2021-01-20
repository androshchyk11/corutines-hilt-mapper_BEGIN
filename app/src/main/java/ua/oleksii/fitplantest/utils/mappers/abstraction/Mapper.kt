package ua.oleksii.fitplantest.utils.mappers.abstraction

interface Mapper<LoginResponse, Login> {

    fun loginResponseToLoginEntity(domainModel: LoginResponse): Login
}