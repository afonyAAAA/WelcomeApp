package ru.afonya.test.ui.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.afonya.test.ui.viewModel.OnboardingViewModel

val appModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    viewModelOf(::OnboardingViewModel)
}