package com.robosoft.virtuallearnproject.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.robosoft.virtuallearnproject.databinding.ActivityLoginRegisterContainerBinding
import com.robosoft.virtuallearnproject.ui.fragments.Search.HomeToSearchScreenFragment
import com.robosoft.virtuallearnproject.ui.fragments.loginregister.LoginRegisterChoiceFragment
import com.robosoft.virtuallearnproject.ui.fragments.moduletest.ModuleTestFragment


class
LoginRegisterContainerActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginRegisterContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginRegisterContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                binding.fragmentContainerLogin.id,
                LoginRegisterChoiceFragment()
            ).commit()
        }
    }
}
