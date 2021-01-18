package ua.oleksii.fitplantest.workers

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ua.oleksii.fitplantest.managers.SharedPreferencesManager
import ua.oleksii.fitplantest.model.network.ApiRequestService
import ua.oleksii.fitplantest.utils.FitAppLogger
import javax.inject.Inject

// Example with Worker + Coroutine
class TestWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private var apiRequestService: ApiRequestService
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        withContext(Dispatchers.IO) {
            delay(1000L)
            try {
                val response = apiRequestService.getPlanDetails("12345")
                FitAppLogger.showLog("TestWorker response code ${response.code()}")
            } catch (e: Exception) {
                FitAppLogger.showLog("TestWorker Exception ${e.localizedMessage}")
            }
        }
        return Result.success()
    }

}