package com.gamil.moahear.digibazaar.di

import com.gamil.moahear.digibazaar.viewmodel.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SignUpViewModel() }
}