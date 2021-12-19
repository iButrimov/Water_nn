package com.example.water_nn.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.rules.TestRule

open class BaseViewModelTest {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
}