package com.calleserpis.coffeetracker.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.calleserpis.coffeetracker.data.CoffeeDatabase
import com.calleserpis.coffeetracker.data.MIGRATION_1_2
import com.calleserpis.coffeetracker.data.MIGRATION_2_3
import com.calleserpis.coffeetracker.data.datastore.CoffeePreferencesManager
import com.calleserpis.coffeetracker.data.notification.NotificationHelper
import com.calleserpis.coffeetracker.data.repository.CoffeeRepositoryImpl
import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository
import com.calleserpis.coffeetracker.domain.use_case.AddAchievementUseCase
import com.calleserpis.coffeetracker.domain.use_case.AddCoffeeUseCase
import com.calleserpis.coffeetracker.domain.use_case.CoffeeUseCases
import com.calleserpis.coffeetracker.domain.use_case.DeleteCoffeeUseCase
import com.calleserpis.coffeetracker.domain.use_case.GetAllAchievementsUseCase
import com.calleserpis.coffeetracker.domain.use_case.GetAllTimeTypeStatsUseCase
import com.calleserpis.coffeetracker.domain.use_case.GetCoffeeByIdUseCase
import com.calleserpis.coffeetracker.domain.use_case.GetCoffeeCount
import com.calleserpis.coffeetracker.domain.use_case.GetLasCoffeeUseCase
import com.calleserpis.coffeetracker.domain.use_case.GetLast12MonthsStatsUseCase
import com.calleserpis.coffeetracker.domain.use_case.GetLastCoffeePrefUseCase
import com.calleserpis.coffeetracker.domain.use_case.GetLastNCoffeesUseCase
import com.calleserpis.coffeetracker.domain.use_case.GetLastNDaysStatsUseCase
import com.calleserpis.coffeetracker.domain.use_case.GetMoneySpent
import com.calleserpis.coffeetracker.domain.use_case.SaveLastCoffeePrefUseCase
import com.calleserpis.coffeetracker.domain.use_case.ShowNotificationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        )
            .addMigrations(MIGRATION_1_2)
            .addMigrations(MIGRATION_2_3)
            .build()
    }
    @Provides
    @Singleton
    fun provideCoffeePreferencesManager(
        @ApplicationContext context: Context
    ): CoffeePreferencesManager {
        return CoffeePreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideCoffeeRepository(db: CoffeeDatabase,
                                preferencesManager: CoffeePreferencesManager): CoffeeRepository {
        return CoffeeRepositoryImpl(db.coffeeDao,db.achievementDao, preferencesManager)
    }

    @Provides
    @Singleton
    fun provideCoffeeUseCases(repository: CoffeeRepository,notificationHelper: NotificationHelper): CoffeeUseCases {
        return CoffeeUseCases(
            addCoffeeUseCase = AddCoffeeUseCase(repository),
            getCoffeeCount = GetCoffeeCount(repository),
            getLasCoffeeUseCase = GetLasCoffeeUseCase(repository),
            getLastNDaysCoffeeUseCase = GetLastNDaysStatsUseCase(repository),
            getLastNCoffeesUseCase = GetLastNCoffeesUseCase(repository),
            getAllTimeTypeStatsUseCase = GetAllTimeTypeStatsUseCase(repository),
            deleteCoffeeUseCase = DeleteCoffeeUseCase(repository),
            getLast12MonthsStatsUseCase = GetLast12MonthsStatsUseCase(repository),
            getMoneySpent = GetMoneySpent(repository),
            getCoffeeByIdUseCase = GetCoffeeByIdUseCase(repository),
            getLastCoffeePrefUseCase = GetLastCoffeePrefUseCase(repository),
            saveLastCoffeePrefUseCase = SaveLastCoffeePrefUseCase(repository),
            showNotificationUseCase = ShowNotificationUseCase(notificationHelper),
            getAllAchievementsUseCase = GetAllAchievementsUseCase(repository),
            addAchievementUseCase = AddAchievementUseCase(repository)
        )
    }


}