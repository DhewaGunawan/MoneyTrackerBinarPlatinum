package com.example.binarchplatinum.ui.main.adapter

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.Preference
import android.util.Log
import com.example.binarchplatinum.data.pref.UserPreference
import com.example.binarchplatinum.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        setTitleName()

    }

    private fun setTitleName () {
        val name = intent.getStringExtra(EXTRAS_NAME)
        if (name != null) {
            Log.d("TAG", name)
            binding.tvName.setText(name)
        }
    }

    companion object {
        private const val EXTRAS_NAME = "EXTRAS_NAME"

        fun startActivity(context: Context, name: String) {
            context.startActivity(Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRAS_NAME,name)
            })
        }
    }
}