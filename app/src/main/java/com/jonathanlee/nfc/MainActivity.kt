package com.jonathanlee.nfc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.jonathanlee.nfc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val nfcHelper = NfcHelper()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNfc()
        initView()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.also { nfcHelper.handleIntent(intent) }
    }

    private fun initView() {
        binding.btnCheck.setOnClickListener {
            val isEnabled = NfcToggleHelper.isPermissionGiven(this)
            binding.tv.text = if (isEnabled) "enabled" else "disabled"
        }
    }

    private fun initNfc() {
        var count = 0
        nfcHelper.apply {
            init(this@MainActivity, binding)
            uidLiveData.observe(this@MainActivity, Observer {
                val string = "count: ${++count}\nrfid: $it"
                binding.tv.text = string
            })
        }
    }
}