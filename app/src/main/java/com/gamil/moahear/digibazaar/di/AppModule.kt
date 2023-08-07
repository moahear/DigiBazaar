package com.gamil.moahear.digibazaar.di

import androidx.room.Room
import com.gamil.moahear.digibazaar.data.datastore.DataStoreImpl
import com.gamil.moahear.digibazaar.data.datastore.IDataStoreRepository
import com.gamil.moahear.digibazaar.data.db.AppDatabase
import com.gamil.moahear.digibazaar.data.repository.product.IProductRepository
import com.gamil.moahear.digibazaar.data.repository.product.ProductRepositoryImpl
import com.gamil.moahear.digibazaar.data.repository.user.IUserRepository
import com.gamil.moahear.digibazaar.data.repository.user.UserRepositoryImpl
import com.gamil.moahear.digibazaar.utils.Constants
import com.gamil.moahear.digibazaar.viewmodel.MainViewModel
import com.gamil.moahear.digibazaar.viewmodel.SignInViewModel
import com.gamil.moahear.digibazaar.viewmodel.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { DataStoreImpl(androidContext()) } bind IDataStoreRepository::class
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    single { baseUrl }
    single { networkTimeout }
    single { provideGson() }
    single { provideInterceptor() }
    single { provideClient(get(),get()) }
    single { provideRetrofit(get(),get(),get()) }
    single { UserRepositoryImpl(get(),get()) } bind IUserRepository::class
    single { Room.databaseBuilder(androidContext(),AppDatabase::class.java, Constants.APP_DATABASE).allowMainThreadQueries().build() }
    single { ProductRepositoryImpl(get(),get<AppDatabase>().getDao()) } bind IProductRepository::class
    viewModel {(hasInternet:Boolean)-> MainViewModel(get(),hasInternet) }


}