package com.example.myapplication.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySettingsBinding
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.android.ext.android.getKoin
import java.util.*

class SettingsActivity : AppCompatActivity(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var _binding: ActivitySettingsBinding
    private val binding
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        loadLanguage()
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        binding.apply {
            btn_back.setOnClickListener {
                onBackPressed()
            }
        }
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == LANGUAGE_KEY) {
            when (sharedPreferences?.getString(key, EN_LOCALE)) {
                RU_LOCALE -> setLocale(RU_LOCALE)
                EN_LOCALE -> setLocale(EN_LOCALE)
            }
        }
        if (key == THEME_KEY) {
            when (sharedPreferences?.getBoolean(key, SHARED_THEME_ON)!!) {
                false -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    saveTheme(false)
                }
                true -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    saveTheme(true)
                }
            }
        }
    }

    override fun onBackPressed() {
        PreferenceManager.getDefaultSharedPreferences(this@SettingsActivity)
            .unregisterOnSharedPreferenceChangeListener(this@SettingsActivity)
        val intent = Intent(getKoin().get(), MainActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }

    private fun loadLanguage() {
        val sharedPreferences = getSharedPreferences(SETTINGS_BUNDLE, Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString(SETTINGS_LANGUAGE, EN_LOCALE)

        val locale = Locale(language!!)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }

    private fun saveTheme(nightTheme: Boolean) {
        val editor = getSharedPreferences(SETTINGS_BUNDLE, Context.MODE_PRIVATE).edit()
        editor.putBoolean(SETTINGS_THEME, nightTheme)
        editor.apply()
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        recreate()

        val editor = getSharedPreferences(SETTINGS_BUNDLE, Context.MODE_PRIVATE).edit()
        editor.putString(SETTINGS_LANGUAGE, lang)
        editor.apply()
    }

    companion object {
        private const val SHARED_THEME_ON = false
        private const val LANGUAGE_KEY = "language"
        private const val RU_LOCALE = "ru"
        private const val EN_LOCALE = "en"
        private const val THEME_KEY = "theme"
        private const val SETTINGS_BUNDLE = "Settings"
        private const val SETTINGS_LANGUAGE = "My_lang"
        private const val SETTINGS_THEME = "My_theme"
    }
}