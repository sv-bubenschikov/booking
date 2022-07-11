package com.example.bookingapp.di

import com.example.bookingapp.data.repositories.BookingRepositoryImpl
import com.example.bookingapp.data.repositories.CompanyRepositoryImpl
import com.example.bookingapp.data.repositories.PlaceRepositoryImpl
import com.example.bookingapp.data.repositories.UserRepositoryImpl
import com.example.bookingapp.domain.repositories_interface.BookingRepository
import com.example.bookingapp.domain.repositories_interface.CompanyRepository
import com.example.bookingapp.domain.repositories_interface.PlaceRepository
import com.example.bookingapp.domain.repositories_interface.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideBookingRepository(bookingRepositoryImpl: BookingRepositoryImpl):
            BookingRepository

    @Binds
    abstract fun provideCompanyRepository(companyRepositoryImpl: CompanyRepositoryImpl):
            CompanyRepository

    @Binds
    abstract fun providePlaceRepository(placeRepositoryImpl: PlaceRepositoryImpl):
            PlaceRepository

    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl):
            UserRepository
}