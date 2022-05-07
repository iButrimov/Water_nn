package com.example.water_nn

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.water_nn.data.database.AddressDatabase
import com.example.water_nn.data.database.AppDatabase
import com.example.water_nn.data.repositories.AddressRepositoryImpl
import com.example.water_nn.data.repositories.OrderRepositoryImpl
import com.example.water_nn.data.repositories.PriceRepositoryImpl
import com.example.water_nn.data.repositories.UserRepositoryImpl
import com.example.water_nn.domain.common.exception.DispatcherType
import com.example.water_nn.domain.repositories.IRepository
import com.example.water_nn.domain.usecases.*
import com.example.water_nn.domain.usecases.address.*
import com.example.water_nn.presentation.authorisation.AuthViewModel
import com.example.water_nn.presentation.main.history.NewOrderViewModel
import com.example.water_nn.presentation.main.mainscreen.MainScreenViewModel
import com.example.water_nn.presentation.main.mainscreen.address.AddressViewModel
import com.example.water_nn.presentation.main.orders.OrdersViewModel
import com.example.water_nn.presentation.main.profile.ProfileViewModel
import com.example.water_nn.presentation.splash.SplashViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataModule = module {
    single { OrderRepositoryImpl(get()) } bind IRepository.OrderRepository::class
    single { UserRepositoryImpl(get()) } bind IRepository.UserRepository::class
    single { PriceRepositoryImpl() } bind IRepository.PriceRepository::class
    single { AddressRepositoryImpl(get(), get()) } bind IRepository.AddressRepository::class
    single { getOrdersDatabase(get()) }
    single { getAddressDatabase(get()) }
    single { getSharedPreferences(get()) }
}

private val domainModule = module {
    //User use cases
    factory { CreateNewUserUseCase(get()) }
    factory { ValidateAuthDataUseCase() }
    factory { CheckUserCreatedUseCase(get()) }
    factory { GetLocalUserInfoUseCase(get()) }
    factory { SaveUserInformationUseCase(get()) }

    //Order use cases
    factory { CreateNewOrderUseCase(get()) }
    factory { GetOrderByIdUseCase(get()) }
    factory { GetAllOrdersUseCase(get()) }
    factory { DeleteOrderUseCase(get()) }
    factory { ValidateNewOrderDataUseCase() }
    factory { GetPriceFullBottleUseCase(get()) }
    factory { GetPriceEmptyBottleUseCase(get()) }

    //Address use cases
    factory { AddNewAddressUseCase(get()) }
    factory { ChangeSelectedAddressUseCase(get()) }
    factory { ConfirmSelectedAddressUseCse(get()) }
    factory { GetAddressListBySelectedUseCase(get()) }
    factory { GetAddressListUseCase(get()) }
    factory { GetLastSelectedAddressUseCase(get()) }
}

private val viewModelModule = module {
    viewModel { NewOrderViewModel(get(), get(), get(), get(), get()) }
    viewModel { AuthViewModel(get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { OrdersViewModel(get()) }
    viewModel { MainScreenViewModel() }
    viewModel { AddressViewModel(get(), get(), get(), get(), get()) }
}

val coroutinesModule = module {
    single(DispatcherType.UI.qualifier) { Dispatchers.Main }
    single(DispatcherType.BACKGROUND.qualifier) { Dispatchers.Default }
}

val modules = listOf(
    dataModule,
    domainModule,
    viewModelModule,
    coroutinesModule
)

private fun getOrdersDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "ordersDatabase")
        .fallbackToDestructiveMigration()
        .build()

private fun getAddressDatabase(context: Context): AddressDatabase =
    Room.databaseBuilder(context.applicationContext, AddressDatabase::class.java, "addressDatabase")
        .fallbackToDestructiveMigration()
        .build()

private fun getSharedPreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(USER_SHARED_PREFERENCES, Context.MODE_PRIVATE)

private const val USER_SHARED_PREFERENCES = "user_shared_preferences"