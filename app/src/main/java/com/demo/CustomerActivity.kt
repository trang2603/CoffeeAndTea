package com.demo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.demo.databinding.ActivityCustomerBinding

class CustomerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}