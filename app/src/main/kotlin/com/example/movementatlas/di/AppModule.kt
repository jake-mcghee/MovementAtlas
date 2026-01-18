package com.example.movementatlas.di

import com.example.movementatlas.domain.data.DefaultStepProvider
import com.example.movementatlas.domain.data.DefaultStepUnitProvider
import com.example.movementatlas.domain.entity.StepUnit
import com.example.movementatlas.domain.usecase.GenerateSequencesUseCase
import com.example.movementatlas.domain.usecase.GetCompatibleNextStepUnitsUseCase
import com.example.movementatlas.domain.usecase.GetStepUnitEntriesUseCase
import com.example.movementatlas.domain.usecase.GetStepUnitExitsUseCase
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
    fun provideStepUnits(): List<@JvmSuppressWildcards StepUnit> {
        val steps = DefaultStepProvider.getDefaultSteps()
        return DefaultStepUnitProvider.getDefaultStepUnits(steps)
    }

    @Provides
    @Singleton
    fun provideGenerateSequencesUseCase(
        stepUnits: List<@JvmSuppressWildcards StepUnit>
    ): GenerateSequencesUseCase {
        return GenerateSequencesUseCase(stepUnits)
    }

    @Provides
    @Singleton
    fun provideGetCompatibleNextStepUnitsUseCase(
        stepUnits: List<@JvmSuppressWildcards StepUnit>
    ): GetCompatibleNextStepUnitsUseCase {
        return GetCompatibleNextStepUnitsUseCase(stepUnits)
    }

    @Provides
    @Singleton
    fun provideGetStepUnitEntriesUseCase(): GetStepUnitEntriesUseCase {
        return GetStepUnitEntriesUseCase()
    }

    @Provides
    @Singleton
    fun provideGetStepUnitExitsUseCase(): GetStepUnitExitsUseCase {
        return GetStepUnitExitsUseCase()
    }
}
