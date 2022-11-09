package com.example.binarchplatinum.pkg.login

import android.os.Bundle
import android.widget.Toast
import com.example.binarchplatinum.base.BaseActivity
import com.example.binarchplatinum.data.localpref.UserPreference
import com.example.binarchplatinum.databinding.ActivityLoginBinding
import com.example.binarchplatinum.pkg.home.ui.HomeActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    private val preference: UserPreference by lazy {
        UserPreference(this@LoginActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        setOnclickListener()
    }

    private fun setOnclickListener() {
        binding.btnName.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            if (name.isEmpty() || name == "") {
                Toast.makeText(this, "Please input your name !", Toast.LENGTH_SHORT).show()
            } else {
                preference.setUserToken(name)
                navigateToMenu(name)
            }
        }
    }

    private fun navigateToMenu(name: String) {
        HomeActivity.startActivity(this, name)
    }
}