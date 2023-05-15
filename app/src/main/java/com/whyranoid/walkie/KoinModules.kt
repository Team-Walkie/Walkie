package com.whyranoid.walkie

import com.whyranoid.data.datasource.ChallengeDataSourceImpl
import com.whyranoid.data.repository.ChallengeRepositoryImpl
import com.whyranoid.domain.datasource.ChallengeDataSource
import com.whyranoid.domain.repository.ChallengeRepository
import com.whyranoid.domain.usecase.GetChallengeDetailUseCase
import com.whyranoid.domain.usecase.GetChallengingPreviewsUseCase
import com.whyranoid.domain.usecase.GetNewChallengePreviewsUseCase
import com.whyranoid.presentation.viewmodel.ChallengeDetailViewModel
import com.whyranoid.presentation.viewmodel.ChallengeMainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { ChallengeMainViewModel(get(), get()) }
    single { ChallengeDetailViewModel(get()) }
}

val repositoryModule = module {
    single<ChallengeRepository> { ChallengeRepositoryImpl(get()) }
}

val dataSourceModule = module {
    single<ChallengeDataSource> { ChallengeDataSourceImpl() }
}

val useCaseModule = module {
    single { GetNewChallengePreviewsUseCase(get()) }
    single { GetChallengingPreviewsUseCase(get()) }
    single { GetChallengeDetailUseCase(get()) }
}