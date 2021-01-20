package ua.oleksii.fitplantest.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_plan_details.*
import ua.oleksii.fitplantest.R
import ua.oleksii.fitplantest.databinding.ActivityPlanDetailsBinding
import ua.oleksii.fitplantest.eventbus.MessageEventBus
import ua.oleksii.fitplantest.eventbus.eventmodels.ShowingImagesOptionEvent
import ua.oleksii.fitplantest.managers.SharedPreferencesManager
import ua.oleksii.fitplantest.model.entities.planDetails.PlanDetail
import ua.oleksii.fitplantest.model.entities.planItem.PlanItem
import ua.oleksii.fitplantest.utils.DataState
import ua.oleksii.fitplantest.utils.FitAppLogger
import ua.oleksii.fitplantest.utils.extensions.showToast
import ua.oleksii.fitplantest.view.adapters.recyclerview.PlansAdapter
import ua.oleksii.fitplantest.viewmodel.PlanDetailsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class PlanDetailActivity : BaseActivity() {

    companion object {
        var planIdKey = "planIdKey"
        var titleKey = "titleKey"
    }

    private lateinit var binding: ActivityPlanDetailsBinding

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    @Inject
    lateinit var plansAdapter: PlansAdapter

    @Inject
    lateinit var viewModel: PlanDetailsViewModel

    private var planId: String? = ""
    private var planTitle: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plan_details)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.needShowImages = sharedPreferencesManager.isWithImages

        parseIntent()
        setupToolbar()
        setupSwipeRefresh()
        setupEventBusCallbacks()
        setupViewModelCallbacks()

        if (viewModel.planId.value.isNullOrEmpty()) {
            viewModel.planId.value = planId
            viewModel.getPlansDetails(planId.toString())
        }

        FitAppLogger.showLog(
            "PlanDetailActivity " +
                    "sharedPreferencesManager ${sharedPreferencesManager.hashCode()} " +
                    "viewModel hashcode ${viewModel.hashCode()} "
        )

    }

    private fun parseIntent() {
        intent?.let {
            planId = it.getStringExtra(planIdKey)
            planTitle = intent.getStringExtra(titleKey)
            if (planId.isNullOrEmpty()) {
                finish()
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbarPlanDetails)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = planTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        toolbarPlanDetails?.navigationIcon =
            ContextCompat.getDrawable(this@PlanDetailActivity, R.drawable.ic_arrow_back)
        toolbarPlanDetails?.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupSwipeRefresh() {
        swiperefreshPlandetails.setColorSchemeResources(
            R.color.colorPrimary,
            R.color.colorAccent,
            R.color.colorPrimaryDark
        )
    }

    private fun setupEventBusCallbacks() {
        compositeDisposable.add(
            MessageEventBus.INSTANCE
                .toObservable()
                .subscribeBy {
                    when (it) {
                        is ShowingImagesOptionEvent -> {
                            binding.needShowImages = sharedPreferencesManager.isWithImages
                        }
                    }
                })
    }

    private fun setupViewModelCallbacks() {
        viewModel.apply {

            planDetailsDataState.observe(this@PlanDetailActivity) { dataState ->
                when (dataState) {
                    is DataState.Success<PlanDetail> -> {
                        binding.entity = dataState.data
                    }
                    is DataState.Loading -> {
                        applicationContext.showToast("Loading")
                    }
                    is DataState.Error -> {
                        applicationContext.showToast(dataState.exception.message.toString())
                    }
                }
            }

//            requestError.observe(this@PlanDetailActivity, Observer {
//                it?.let {
//                    showErrorMessage(it, View.OnClickListener { requestError.value = null })
//                }
//            })

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_plans_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> startActivity(
                Intent(
                    this@PlanDetailActivity,
                    SettingsActivity::class.java
                )
            )
        }
        return super.onOptionsItemSelected(item)
    }

}
