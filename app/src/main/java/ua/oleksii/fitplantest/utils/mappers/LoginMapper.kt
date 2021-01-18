package ua.oleksii.fitplantest.utils.mappers

interface LoginMapper<LoginResponse, Login> {

    fun loginResponseToLoginEntity(domainModel: LoginResponse): Login
}