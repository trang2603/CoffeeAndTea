package com.demo

import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.view.Display
import androidx.appcompat.app.AppCompatActivity
import com.demo.databinding.ActivityMainBinding
import com.demo.fragment.FragmentAccount
import com.demo.fragment.FragmentBill
import com.demo.fragment.FragmentChart
import com.demo.fragment.FragmentHome

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigationForLandscape()

        val displayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val displays = displayManager.displays

        if (displays.size > 1) {
            val intentCustomer = Intent(this, CustomerActivity::class.java)
            intentCustomer.flags =
                Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            startActivity(intentCustomer, createActivityOptions(displays[1]))
        }
    }

    private fun createActivityOptions(display: Display): Bundle {
        val activityOptions = android.app.ActivityOptions.makeBasic()
        activityOptions.launchDisplayId = display.displayId
        return activityOptions.toBundle()
    }

    private fun setupNavigationForLandscape() {
        binding.navigationView.setNavigationItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, FragmentHome())
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_bill -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, FragmentBill())
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_chart -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, FragmentChart())
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_account -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, FragmentAccount())
                        .addToBackStack(null)
                        .commit()
                }
            }
            menu.isChecked = true
            true
        }
    }
}
