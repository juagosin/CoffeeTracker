package com.juagosin.coffeetracker.di

import android.app.Application
import androidx.room.Room
import com.juagosin.coffeetracker.data.CoffeeDatabase
import com.juagosin.coffeetracker.data.repository.CoffeeRepositoryImpl
import com.juagosin.coffeetracker.domain.repository.CoffeeRepository
import com.juagosin.coffeetracker.domain.use_case.AddCoffeeUseCase
import com.juagosin.coffeetracker.domain.use_case.CoffeeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoffeeDatabase(app: Application): CoffeeDatabase {
        return Room.databaseBuilder(
            app,
            CoffeeDatabase::class.java,
            "coffee_db"
        ).fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideCoffeeRepository(db: CoffeeDatabase): CoffeeRepository {
        return CoffeeRepositoryImpl(db.coffeeDao)
    }

    @Provides
    @Singleton
    fun provideCoffeeUseCases(repository: CoffeeRepository): CoffeeUseCases {
        return CoffeeUseCases(
            addCoffeeUseCase = AddCoffeeUseCase(repository),
        )
    }


}