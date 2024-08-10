package com.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.databinding.ActivityEmployeeBinding

class EmployeeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
