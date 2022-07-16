package com.example.myapplication.activities

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val binding
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        loadLanguage()
        super.onResume()
    }

    private fun loadLanguage() {
        val sharedPreferences = getSharedPreferences(SETTINGS_BUNDLE, Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString(SETTINGS_LANGUAGE, GET_LANGUAGE)

        val locale = Locale(language!!)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }

    companion object {
        private const val GET_LANGUAGE = "en"
        private const val SETTINGS_BUNDLE = "Settings"
        private const val SETTINGS_LANGUAGE = "My_lang"
    }
}