package ua.oleksii.fitplantest.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_settings.*
import ua.oleksii.fitplantest.R
import ua.oleksii.fitplantest.eventbus.MessageEventBus
import ua.oleksii.fitplantest.eventbus.eventmodels.ShowingImagesOptionEvent
import ua.oleksii.fitplantest.managers.SharedPreferencesManager
import ua.oleksii.fitplantest.utils.glide.GlideApp
import ua.oleksii.fitplantest.view.activities.auth.LoginActivity
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : BaseActivity(), CompoundButton.OnCheckedChangeListener {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setupToolbar()
        setupDefaultValues()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbarSettings)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = getString(R.string.settings_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        toolbarSettings.navigationIcon = ContextCompat.getDrawable(this@SettingsActivity, R.drawable.ic_arrow_back)
        toolbarSettings.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupDefaultValues() {
        withImagesSwitch.isChecked = sharedPreferencesManager.isWithImages
        withImagesSwitch.setOnCheckedChangeListener(this)

        withImagesButton.setOnClickListener {
            withImagesSwitch.isChecked = !withImagesSwitch.isChecked
        }

        logoutButton.setOnClickListener {
            sharedPreferencesManager.clearAllSharedPreferences()
            GlideApp.get(applicationContext).clearMemory()
            finishAffinity()
            val intentToLogin = Intent(this@SettingsActivity, LoginActivity::class.java)
            startActivity(intentToLogin)
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        sharedPreferencesManager.isWithImages = isChecked
        MessageEventBus.INSTANCE.send(ShowingImagesOptionEvent())
    }
}
