package com.example.bookingapp.di

import com.example.bookingapp.data.repositories.*
import com.example.bookingapp.domain.repositories_interface.*
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

    @Binds
    abstract fun provideDateRepository(dateRepositoryImpl: DateRepositoryImpl):
            DateRepository
}