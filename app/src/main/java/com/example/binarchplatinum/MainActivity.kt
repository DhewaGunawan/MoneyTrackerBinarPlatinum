package com.example.binarchplatinum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.binarchplatinum.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    companion object{
        const val EXTRA_NAME: String = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        setTitleName()
    }

    private fun setTitleName () {
        val name = intent.getStringExtra(EXTRA_NAME)
        binding.tvName.text = buildString {
            append(name)
            append("Yorda")
        }
    }
}