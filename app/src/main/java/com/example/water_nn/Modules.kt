package com.example.water_nn

import android.content.Context
import androidx.room.Room
import com.example.water_nn.data.database.AppDatabase
import com.example.water_nn.data.repositories.OrderRepository
import com.example.water_nn.domain.repositories.IRepository
import com.example.water_nn.domain.usecases.AddNewOrderUseCase
import com.example.water_nn.domain.usecases.DeleteOrderUseCase
import com.example.water_nn.domain.usecases.GetAllOrdersUseCase
import com.example.water_nn.domain.usecases.GetOrderUseCase
import com.example.water_nn.presentation.main.order.OrderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataModule = module {
    single { OrderRepository(get()) } bind IRepository.LocalRepository::class
    single { getOrdersDatabase(get()) }
}

private val domainModule = module {
    factory { AddNewOrderUseCase(get()) }
    factory { GetOrderUseCase(get()) }
    factory { GetAllOrdersUseCase(get()) }
    factory { DeleteOrderUseCase(get()) }
}

private val viewModelModule = module {
    viewModel { OrderViewModel(get(), get(), get(), get()) }
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