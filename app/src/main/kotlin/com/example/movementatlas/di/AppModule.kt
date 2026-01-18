package com.example.movementatlas.di

import com.example.movementatlas.data.StepRepositoryAndroidImpl
import com.example.movementatlas.domain.repository.StepRepository
import com.example.movementatlas.domain.repository.StateTransitionRules
import com.example.movementatlas.domain.service.StateTransitionRulesImpl
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
    fun provideStateTransitionRules(): StateTransitionRules {
        // Transition rules are pure domain logic, no platform-specific implementation needed
        return StateTransitionRulesImpl()
    }

    @Provides
    @Singleton
    fun provideGenerateSequencesUseCase(
        stepRepository: StepRepository,
        transitionRules: StateTransitionRules
    ): GenerateSequencesUseCase {
        return GenerateSequencesUseCase(stepRepository, transitionRules)
    }

    @Provides
    @Singleton
    fun provideGetCompatibleNextStepsUseCase(
        stepRepository: StepRepository,
        transitionRules: StateTransitionRules
    ): GetCompatibleNextStepsUseCase {
        return GetCompatibleNextStepsUseCase(stepRepository, transitionRules)
    }

    @Provides
    @Singleton
    fun provideGetStepEntriesUseCase(
        transitionRules: StateTransitionRules
    ): GetStepEntriesUseCase {
        return GetStepEntriesUseCase(transitionRules)
    }

    @Provides
    @Singleton
    fun provideGetStepExitsUseCase(
        transitionRules: StateTransitionRules
    ): GetStepExitsUseCase {
        return GetStepExitsUseCase(transitionRules)
    }
}
