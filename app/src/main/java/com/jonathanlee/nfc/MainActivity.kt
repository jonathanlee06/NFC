package com.jonathanlee.nfc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jonathanlee.nfc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.btnCheck.setOnClickListener {
            val isEnabled = NfcToggleHelper.isPermissionGiven(this)
            binding.tv.text = if (isEnabled) "enabled" else "disabled"
        }
    }
}