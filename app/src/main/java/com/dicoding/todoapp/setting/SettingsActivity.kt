package com.dicoding.todoapp.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.dicoding.todoapp.R
import com.dicoding.todoapp.notification.NotificationWorker
import com.dicoding.todoapp.utils.NOTIFICATION_CHANNEL_ID
import java.util.concurrent.TimeUnit

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, SettingsFragment())
                    .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
            val prefNightMode = findPreference<SwitchPreference>(getString(R.string.pref_key_night_mode))
            prefNightMode?.setOnPreferenceChangeListener { _ , newValue ->

                if (newValue as Boolean) {
                    updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
                }
                true
            }

            prefNotification?.setOnPreferenceChangeListener { _, newValue ->
                val channelName = getString(R.string.notify_channel_name)
                //TODO 13 : Schedule and cancel daily reminder using WorkManager with data channelName  //Done


                val workManager = WorkManager.getInstance(requireContext())

                val data = Data.Builder()
                        .putString(NOTIFICATION_CHANNEL_ID, channelName)
                        .build()

                val periodicWorkRequest = PeriodicWorkRequest.Builder(NotificationWorker::class.java, 1, TimeUnit.DAYS)
                        .setInputData(data)
                        .build()

                if (newValue as Boolean) {
                    workManager.enqueue(periodicWorkRequest)
                } else {
                    workManager.cancelWorkById(periodicWorkRequest.id)
                }
                true
            }

        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}