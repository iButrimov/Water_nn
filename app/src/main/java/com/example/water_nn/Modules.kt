package com.example.water_nn

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.water_nn.data.database.AppDatabase
import com.example.water_nn.data.repositories.OrderRepository
import com.example.water_nn.data.repositories.PriceRepository
import com.example.water_nn.data.repositories.UserRepository
import com.example.water_nn.domain.repositories.IRepository
import com.example.water_nn.domain.usecases.*
import com.example.water_nn.presentation.authorisation.AuthViewModel
import com.example.water_nn.presentation.main.history.AllOrdersViewModel
import com.example.water_nn.presentation.main.history.NewOrderViewModel
import com.example.water_nn.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataModule = module {
    single { OrderRepository(get()) } bind IRepository.LocalRepository::class
    single { UserRepository(get()) } bind IRepository.UserRepository::class
    single { PriceRepository() } bind IRepository.PriceRepository::class
    single { getOrdersDatabase(get()) }
    single { getSharedPreferences(get()) }
}

private val domainModule = module {
    factory { CreateNewOrderUseCase(get()) }
    factory { GetOrderByIdUseCase(get()) }
    factory { GetAllOrdersUseCase(get()) }
    factory { DeleteOrderUseCase(get()) }
    factory { ValidateNewOrderDataUseCase(get()) }
    factory { ValidateAuthDataUseCase(get()) }
    factory { CreateNewUserUseCase(get()) }
    factory { CheckUserCreatedUseCase(get()) }
    factory { GetPriceFullBottleUseCase(get()) }
    factory { GetPriceEmptyBottleUseCase(get()) }
}

private val viewModelModule = module {
    viewModel { AllOrdersViewModel(get(), get(), get()) }
    viewModel { NewOrderViewModel(get(), get(), get(), get(), get()) }
    viewModel { AuthViewModel(get(), get()) }
    viewModel { SplashViewModel(get()) }
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

private const val USER_SHARED_PREFERENCES = "user_shared_preferences"

private fun getSharedPreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(USER_SHARED_PREFERENCES, Context.MODE_PRIVATE)