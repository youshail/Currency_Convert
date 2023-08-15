package com.example.currency.convert.xml.compose.di


import com.example.currency.convert.xml.compose.common.Constants
import com.example.currency.convert.xml.compose.common.DispatcherProvider
import com.example.currency.convert.xml.compose.data.CurrencyApi
import com.example.currency.convert.xml.compose.data.repository.CurrencyRepository
import com.example.currency.convert.xml.compose.data.repository.CurrencyRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCurrencyApi() : CurrencyApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCurrencyRepository(api: CurrencyApi) : CurrencyRepository {
        return CurrencyRepositoryImp(api)
    }

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}