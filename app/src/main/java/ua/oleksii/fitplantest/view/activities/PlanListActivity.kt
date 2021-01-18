package ua.oleksii.fitplantest.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.whenResumed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_plan_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.oleksii.fitplantest.R
import ua.oleksii.fitplantest.application.FitPlanApplication
import ua.oleksii.fitplantest.databinding.ActivityPlanListBinding
import ua.oleksii.fitplantest.eventbus.MessageEventBus
import ua.oleksii.fitplantest.eventbus.eventmodels.ShowingImagesOptionEvent
import ua.oleksii.fitplantest.interfaces.OnRecyclerItemClickListener
import ua.oleksii.fitplantest.managers.ConnectionManager
import ua.oleksii.fitplantest.managers.SharedPreferencesManager
import ua.oleksii.fitplantest.model.entities.PlanItemEntity
import ua.oleksii.fitplantest.model.entities.RequestError
import ua.oleksii.fitplantest.model.network.ApiRequestService
import ua.oleksii.fitplantest.services.TestService
import ua.oleksii.fitplantest.utils.FitAppLogger
import ua.oleksii.fitplantest.utils.extensions.showToast
import ua.oleksii.fitplantest.view.adapters.recyclerview.PlansAdapter
import ua.oleksii.fitplantest.viewmodel.PlanListViewModel
import ua.oleksii.fitplantest.workers.TestWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class PlanListActivity : BaseActivity(), OnRecyclerItemClickListener {

    private lateinit var binding: ActivityPlanListBinding

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    @Inject
    lateinit var connectionManager: ConnectionManager

    @Inject
    lateinit var plansAdapter: PlansAdapter

    private val viewModel: PlanListViewModel by viewModels()

    private var plansList: ArrayList<PlanItemEntity>? = null
    private var connectionSnackbar: Snackbar? = null

    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, ua.oleksii.fitplantest.R.layout.activity_plan_list)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        setupToolbar()
        setupSwipeRefresh()
        setupRecyclerView()
        setupEventBusCallbacks()
        setupViewModelCallbacks()

        createWorkManagerWithRequest()
        createServiceWithRequest()

        FitAppLogger.showLog(
            "PlanListActivity " +
                    "sharedPreferencesManager ${sharedPreferencesManager.hashCode()} " +
                    "connectionMAnager ${connectionManager.hashCode()} " +
                    "viewModel hashcode ${viewModel.hashCode()} "
        )

    }

    private fun setupToolbar() {
        setSupportActionBar(toolbarPlanList)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = getString(ua.oleksii.fitplantest.R.string.plan_list_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setupSwipeRefresh() {
        swipeRefreshPlanList.setColorSchemeResources(
            ua.oleksii.fitplantest.R.color.colorPrimary,
            ua.oleksii.fitplantest.R.color.colorAccent,
            ua.oleksii.fitplantest.R.color.colorPrimaryDark
        )
    }

    private fun setupRecyclerView() {
        plansList = ArrayList()
        plansAdapter.listener = this
        plansAdapter.planItems = plansList
        plansAdapter.isWithImages = sharedPreferencesManager.isWithImages
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerviewPlanlist.layoutManager = linearLayoutManager
        recyclerviewPlanlist.adapter = plansAdapter
    }

    @Suppress("ControlFlowWithEmptyBody")
    @SuppressLint("WrongConstant")
    private fun setupEventBusCallbacks() {
        compositeDisposable.add(MessageEventBus.INSTANCE
            .toObservable()
            .subscribeBy {
                when (it) {
                    is ShowingImagesOptionEvent -> {
                        plansAdapter.isWithImages = sharedPreferencesManager.isWithImages
                        plansAdapter.notifyDataSetChanged()
                    }
                }
            })

        connectionManager.isConnected.observe(this@PlanListActivity, Observer {
            it?.let { connected ->
                if (connected) {
                    if (connectionSnackbar?.isShown == true) {
                        connectionSnackbar?.setText("Connected !")
                        connectionSnackbar?.view?.setBackgroundColor(Color.parseColor("#00FF00"))
                        connectionSnackbar?.duration = Snackbar.LENGTH_LONG
                        connectionSnackbar?.show()
                    } else {
                        // nothing
                    }

                } else {
                    if (connectionSnackbar?.isShown != true) {
                        connectionSnackbar = Snackbar.make(coordinatorLayoutPlanList, "", Snackbar.LENGTH_INDEFINITE)
                    } else {

                    }
                    connectionSnackbar?.setText("Connection lost :(")
                    connectionSnackbar?.view?.setBackgroundColor(Color.parseColor("#FF0000"))
                    connectionSnackbar?.duration = Snackbar.LENGTH_INDEFINITE
                    connectionSnackbar?.show()

                }
            }
        })
    }

    private fun setupViewModelCallbacks() {
        viewModel.apply {

            requestError.observe(this@PlanListActivity, {
                it?.let {
                    showErrorMessage(it) { requestError.value = null }
                }
            })

            planItemsData.observe(this@PlanListActivity, {
                plansList?.clear()
                plansList?.addAll(it)
                plansAdapter.notifyDataSetChanged()
            })

        }
    }

    // test only
    private fun createWorkManagerWithRequest() {
        val testWorkRequest = OneTimeWorkRequestBuilder<TestWorker>()
            // .setInitialDelay(2, TimeUnit.SECONDS)
            .build()

        WorkManager
            .getInstance(this)
            .enqueue(testWorkRequest)
    }

    private fun createServiceWithRequest() {
        startService(Intent(this, TestService::class.java))
    }

    override fun onRecyclerItemClick(position: Int) {
        val intentToPlanDetails = Intent(this@PlanListActivity, PlanDetailActivity::class.java)
        intentToPlanDetails.putExtra(PlanDetailActivity.planIdKey, plansList?.get(position)?.id)
        intentToPlanDetails.putExtra(PlanDetailActivity.titleKey, plansList?.get(position)?.name)
        startActivity(intentToPlanDetails)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_plans_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> startActivity(
                Intent(
                    this@PlanListActivity,
                    SettingsActivity::class.java
                )
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        lifecycleScope.launch {
            if (backPressedOnce) {
                super.onBackPressed()
            } else {
                showToast(getString(R.string.press_back_twice))
                backPressedOnce = true
                delay(2000L)
                backPressedOnce = false
            }
        }
    }
}
