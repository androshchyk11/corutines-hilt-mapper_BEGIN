package ua.oleksii.fitplantest.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import ua.oleksii.fitplantest.managers.SharedPreferencesManager
import ua.oleksii.fitplantest.model.network.ApiRequestService
import ua.oleksii.fitplantest.utils.FitAppLogger
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

// Example with Service + Coroutine
@AndroidEntryPoint
class TestService : Service(), CoroutineScope {

    private var coroutineJob = Job()
    override val coroutineContext = Dispatchers.Default + coroutineJob

    @Inject
    lateinit var apiRequestService: ApiRequestService

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        launch {
            try {
          //      delay(2000L)
                val resposne = apiRequestService.getPlanDetails("qwerty")
                FitAppLogger.showLog("TestService resposne code ${resposne.code()}")
            } catch (e: Exception) {
                FitAppLogger.showLog("TestService Exception ${e.localizedMessage}")
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        FitAppLogger.showLog("TestService onDestroy()")
        coroutineJob.cancel()
    }
}