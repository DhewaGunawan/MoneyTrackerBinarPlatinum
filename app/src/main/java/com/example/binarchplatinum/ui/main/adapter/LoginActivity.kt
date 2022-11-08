package com.example.binarchplatinum.ui.main.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.Preference
import android.widget.Toast
import com.example.binarchplatinum.data.pref.UserPreference
import com.example.binarchplatinum.databinding.ActivityLoginBinding



class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val preference: UserPreference by lazy {
        UserPreference(this@LoginActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setOnclickListener()
        setToken()

    }

    private fun setOnclickListener(){
        binding.btnName.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            if(name.isEmpty()){

                Toast.makeText(this, "Please input your name !", Toast.LENGTH_SHORT).show()
            }else{
                navigateToMenu(name)
            }
        }
    }

    private fun navigateToMenu(name: String) {
        MainActivity.startActivity(this,name)
    }
    private fun setToken(){
        var username = binding.etName.text.toString().trim()
        if (username.isEmpty()){
            preference.getToken()
        }
    }
}