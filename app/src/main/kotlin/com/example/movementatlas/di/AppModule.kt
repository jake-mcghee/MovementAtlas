package com.example.movementatlas.di

import com.example.movementatlas.data.StepRepositoryAndroidImpl
import com.example.movementatlas.data.StepUnitRepositoryAndroidImpl
import com.example.movementatlas.domain.repository.StepRepository
import com.example.movementatlas.domain.repository.StepUnitRepository
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
    fun provideStepRepository(): StepRepository {
        return StepRepositoryAndroidImpl()
    }

    @Provides
    @Singleton
    fun provideStepUnitRepository(
        stepRepository: StepRepository
    ): StepUnitRepository {
        return StepUnitRepositoryAndroidImpl(stepRepository)
    }

    @Provides
    @Singleton
    fun provideGenerateSequencesUseCase(
        stepUnitRepository: StepUnitRepository
    ): GenerateSequencesUseCase {
        return GenerateSequencesUseCase(stepUnitRepository)
    }

    @Provides
    @Singleton
    fun provideGetCompatibleNextStepUnitsUseCase(
        stepUnitRepository: StepUnitRepository
    ): GetCompatibleNextStepUnitsUseCase {
        return GetCompatibleNextStepUnitsUseCase(stepUnitRepository)
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
