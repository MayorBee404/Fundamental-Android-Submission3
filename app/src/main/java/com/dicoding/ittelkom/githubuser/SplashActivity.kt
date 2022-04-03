
package com.dicoding.ittelkom.githubuser
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.ittelkom.githubuser.databinding.ActivitySplashBinding
import com.dicoding.ittelkom.githubuser.ui.listuser.ListUser
import com.dicoding.mydatastore.SettingPreferences
import com.dicoding.mydatastore.SettingViewModel

class SplashActivity : AppCompatActivity() {
    private lateinit var splashBinding: ActivitySplashBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(this).get(
            SettingViewModel::class.java
        )

        viewModel.setPreferences(pref)
        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(applicationContext, ListUser::class.java)
            startActivity(intent)
            finish()
        }, 800L)

    }
}

