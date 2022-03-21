package com.example.water_nn.presentation.authorisation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.water_nn.R

private const val newClearTaskFlags =
    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

class AuthActivity : AppCompatActivity() {

    companion object {
        fun createStartIntent(context: Context): Intent =
            Intent(context, AuthActivity::class.java).apply {
                flags = newClearTaskFlags
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}