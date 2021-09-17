package com.example.water_nn

import android.content.Context
import androidx.room.Room
import com.example.water_nn.data.database.AppDatabase
import com.example.water_nn.data.repositories.OrderRepository
import com.example.water_nn.domain.repositories.IRepository
import com.example.water_nn.domain.usecases.*
import com.example.water_nn.presentation.main.history.AllOrdersViewModel
import com.example.water_nn.presentation.main.history.NewOrderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataModule = module {
    single { OrderRepository(get()) } bind IRepository.LocalRepository::class
    single { getOrdersDatabase(get()) }
}

private val domainModule = module {
    factory { CreateNewOrderUseCase(get()) }
    factory { GetOrderUseCase(get()) }
    factory { GetAllOrdersUseCase(get()) }
    factory { DeleteOrderUseCase(get()) }
    factory { ValidateNewOrderDataUseCase(get()) }
}

private val viewModelModule = module {
    viewModel { AllOrdersViewModel(get(), get(), get()) }
    viewModel { NewOrderViewModel(get(), get()) }
}

val modules = listOf(
    dataModule,
    domainModule,
    viewModelModule
)

private fun getOrdersDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "ordersDatabase")
        .fallbackToDestructiveMigration()
        .build()