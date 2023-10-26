package com.yusufmendes.sisterslabgraduationproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yusufmendes.sisterslabgraduationproject.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}