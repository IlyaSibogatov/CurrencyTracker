package com.example.myapplication.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
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
                PreferenceManager.getDefaultSharedPreferences(this@SettingsActivity)
                    .unregisterOnSharedPreferenceChangeListener(this@SettingsActivity)
                val intent = Intent(getKoin().get(), MainActivity::class.java)
                startActivity(intent)
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
        if (key == "language") {
            when (sharedPreferences?.getString(key, SHARED_LANGUAGE)) {
                "RU" -> setLocale("ru")
                "ENG" -> setLocale("en")
            }
        }
        if (key == "theme") {
            when (sharedPreferences?.getBoolean(key, SHARED_THEME_ON)) {
                false -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    saveTheme(false)
                }
                true -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    saveTheme(true)
                }
                null -> Toast.makeText(
                    this,
                    "Something went wrong",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun saveTheme(nightTheme: Boolean) {
        val editor = getSharedPreferences("Setting", Context.MODE_PRIVATE).edit()
        editor.putBoolean("My_theme", nightTheme)
        editor.apply()
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        recreate()

        val editor = getSharedPreferences("Setting", Context.MODE_PRIVATE).edit()
        editor.putString("My_lang", lang)
        editor.apply()
    }

    companion object {
        private const val SHARED_THEME_ON = false
        private const val SHARED_LANGUAGE = "ENG"
    }
}