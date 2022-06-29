package com.example.myapplication.activities

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        loadSettings()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun loadSettings() {
        val sharedPreferences = getSharedPreferences("Setting", Activity.MODE_PRIVATE)
        val enableNightTheme = sharedPreferences.getBoolean("My_theme", SHARED_THEME_ON)
        val language = sharedPreferences.getString("My_lang", SHARED_LANGUAGE)
        setLocale(language!!)
        setTheme(enableNightTheme)
    }

    private fun setTheme(enableNightTheme: Boolean) {
        when (enableNightTheme) {
            false -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            true -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }

    companion object {
        private const val SHARED_THEME_ON = false
        private const val SHARED_LANGUAGE = "ENG"
    }
}