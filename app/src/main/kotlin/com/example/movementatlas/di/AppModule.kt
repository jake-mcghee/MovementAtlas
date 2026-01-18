package com.example.movementatlas.di

import com.example.movementatlas.data.StepRepositoryAndroidImpl
import com.example.movementatlas.domain.repository.StepRepository
import com.example.movementatlas.domain.usecase.GenerateSequencesUseCase
import com.example.movementatlas.domain.usecase.GetCompatibleNextStepsUseCase
import com.example.movementatlas.domain.usecase.GetStepEntriesUseCase
import com.example.movementatlas.domain.usecase.GetStepExitsUseCase
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
    fun provideGenerateSequencesUseCase(
        stepRepository: StepRepository
    ): GenerateSequencesUseCase {
        return GenerateSequencesUseCase(stepRepository)
    }

    @Provides
    @Singleton
    fun provideGetCompatibleNextStepsUseCase(
        stepRepository: StepRepository
    ): GetCompatibleNextStepsUseCase {
        return GetCompatibleNextStepsUseCase(stepRepository)
    }

    @Provides
    @Singleton
    fun provideGetStepEntriesUseCase(): GetStepEntriesUseCase {
        return GetStepEntriesUseCase()
    }

    @Provides
    @Singleton
    fun provideGetStepExitsUseCase(): GetStepExitsUseCase {
        return GetStepExitsUseCase()
    }
}
