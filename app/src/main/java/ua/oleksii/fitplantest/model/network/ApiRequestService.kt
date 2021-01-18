package ua.oleksii.fitplantest.model.network

import retrofit2.Response
import retrofit2.http.*
import ua.oleksii.fitplantest.model.entities.login.LoginResponse
import ua.oleksii.fitplantest.model.entities.PlanDetailResponse
import ua.oleksii.fitplantest.model.entities.PlanListResponse

/**
 * Created by AlexLampa on 30.05.2018.
 */
interface ApiRequestService {

    @FormUrlEncoded
    @POST(NetworkUrls.LOGIN)
    suspend fun login(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String,
        @Field("username") name: String,
        @Field("password") pass: String
    ): Response<LoginResponse>


    @GET(NetworkUrls.PLANS_LIST)
    suspend fun getPlanList(): Response<PlanListResponse>

    @GET(NetworkUrls.PLAN_DETAILS)
    suspend fun getPlanDetails(@Query("planId") planId: String): Response<PlanDetailResponse>

}
