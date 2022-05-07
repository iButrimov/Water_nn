package com.example.water_nn.presentation.extentions

import android.content.SharedPreferences

fun SharedPreferences.getStringOrEmpty(key: String) = getString(key, "") ?: ""

fun SharedPreferences.getBooleanOrFalse(key: String) = getBoolean(key, false)