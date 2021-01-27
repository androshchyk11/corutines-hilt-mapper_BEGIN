package ua.oleksii.fitplantest.view.activities.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import ua.oleksii.fitplantest.R
import ua.oleksii.fitplantest.databinding.ActivityLoginBinding
import ua.oleksii.fitplantest.managers.SharedPreferencesManager
import ua.oleksii.fitplantest.model.entities.login.Login
import ua.oleksii.fitplantest.utils.DataState
import ua.oleksii.fitplantest.utils.FitAppLogger
import ua.oleksii.fitplantest.utils.extensions.generateProgressDialog
import ua.oleksii.fitplantest.utils.extensions.showToast
import ua.oleksii.fitplantest.view.activities.BaseActivity
import ua.oleksii.fitplantest.view.activities.PlanListActivity
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding
    private var progressDialog: MaterialDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//
//        if (!sharedPreferencesManager.userAccessToken.isNullOrEmpty()) {
//            goToPlansListActivity()
//            return
//        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        viewModel.checkToken()
        FitAppLogger.showLog(
            "LoginActivity SharedPreferencesManager hashcode ${sharedPreferencesManager.hashCode()}" +
                    " LoginViewModel hashcode ${viewModel.hashCode()}"
        )

        progressDialog = generateProgressDialog()
        setupFieldListeners()
        setupViewModelCallbacks()

    }

    private fun setupFieldListeners() {
        editTextEmail.doAfterTextChanged { loginButton.isEnabled = checkFieldsAreValid() }
        editTextPassword.doAfterTextChanged { loginButton.isEnabled = checkFieldsAreValid() }
        loginButton.setOnClickListener { viewModel.authUser() }
    }

    private fun checkFieldsAreValid(): Boolean {
        return !viewModel.password.value.isNullOrEmpty() && viewModel.email.value?.matches(Patterns.EMAIL_ADDRESS.toRegex()) == true
    }

    private fun setupViewModelCallbacks() {
        viewModel.apply {
            loginState.observe(this@LoginActivity, Observer {   loginState ->
                when (loginState) {
                    is DataState.Success<Login> -> {
                        loginState.data.authToken?.let { token ->
                            sharedPreferencesManager.userAccessToken = token
                            goToPlansListActivity()
                        }
                    }
                    is DataState.Loading -> {
                        applicationContext.showToast("Loading")
                    }
                    is DataState.Error -> {
                        applicationContext.showToast(loginState.exception.message.toString())
                    }
                }
            })
            accessToken.observe(this@LoginActivity,Observer{
                if(it.isNotEmpty()){
                    goToPlansListActivity()
                }
            })
        }
    }

    private fun goToPlansListActivity() {
        finishAffinity()
        startActivity(Intent(this, PlanListActivity::class.java))
    }

}
