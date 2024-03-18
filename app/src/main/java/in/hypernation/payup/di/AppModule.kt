package `in`.hypernation.payup.di

import android.content.Context
import `in`.hypernation.payup.data.USSD.USSDApi
import `in`.hypernation.payup.data.USSD.USSDBuilder
import `in`.hypernation.payup.data.repo.HomeRepository
import `in`.hypernation.payup.data.repo.HomeRepositoryImpl
import `in`.hypernation.payup.presentation.home.HomeViewModel
import `in`.hypernation.payup.presentation.permissions.PermissionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { PermissionViewModel() }
    single<USSDApi> {USSDBuilder}
    single<HomeRepository> {HomeRepositoryImpl(get(), get())}
}