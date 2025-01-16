    package `in`.hypernation.payup.di

import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import `in`.hypernation.payup.MainActivity
import `in`.hypernation.payup.data.USSD.USSDApi
import `in`.hypernation.payup.data.USSD.USSDBuilder
import `in`.hypernation.payup.data.local.PreferenceManager
import `in`.hypernation.payup.data.manipulation.StringManipulation
import `in`.hypernation.payup.data.repo.HomeRepository
import `in`.hypernation.payup.data.repo.HomeRepositoryImpl
import `in`.hypernation.payup.data.repo.PaymentRepository
import `in`.hypernation.payup.data.repo.PaymentRepositoryImpl
import `in`.hypernation.payup.presentation.home.HomeViewModel
import `in`.hypernation.payup.presentation.payment.PaymentViewModel
import `in`.hypernation.payup.presentation.permissions.PermissionViewModel
import `in`.hypernation.payup.presentation.result.ResultViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { PermissionViewModel() }
    viewModel { ResultViewModel(get()) }
    viewModel { PaymentViewModel(get(), get()) }
    single<USSDApi> {USSDBuilder}
    single<HomeRepository> {HomeRepositoryImpl(get(), get(), get(), get())}
    single<PaymentRepository> { PaymentRepositoryImpl(get(), get(), get()) }
    single {StringManipulation()}
    single { PreferenceManager(get()) }

    factory {
        GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    }

    factory {
        GmsBarcodeScanning.getClient(get(), get())
    }

    /*scope<HomeViewModel> {
        scoped<GmsBarcodeScannerOptions> { GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        }
        scoped<GmsBarcodeScanner> {
            GmsBarcodeScanning.getClient(get(), get())
        }
    }*/

}