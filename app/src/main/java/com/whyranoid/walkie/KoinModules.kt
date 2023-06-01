package com.whyranoid.walkie

import com.whyranoid.data.datasource.ChallengeDataSourceImpl
import com.whyranoid.data.datasource.PostDataSourceImpl
import com.whyranoid.data.datasource.UserDataSourceImpl
import com.whyranoid.data.repository.ChallengeRepositoryImpl
import com.whyranoid.data.repository.PostRepositoryImpl
import com.whyranoid.data.repository.UserRepositoryImpl
import com.whyranoid.domain.datasource.ChallengeDataSource
import com.whyranoid.domain.datasource.PostDataSource
import com.whyranoid.domain.datasource.UserDataSource
import com.whyranoid.domain.repository.ChallengeRepository
import com.whyranoid.domain.repository.PostRepository
import com.whyranoid.domain.repository.UserRepository
import com.whyranoid.domain.usecase.*
import com.whyranoid.presentation.viewmodel.ChallengeDetailViewModel
import com.whyranoid.presentation.viewmodel.ChallengeExitViewModel
import com.whyranoid.presentation.viewmodel.ChallengeMainViewModel
import com.whyranoid.presentation.viewmodel.UserPageViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { ChallengeMainViewModel(get(), get(), get()) }
    single { ChallengeDetailViewModel(get()) }
    single { ChallengeExitViewModel(get()) }
    single { UserPageViewModel(get(), get(), get(), get()) }
}

val repositoryModule = module {
    single<ChallengeRepository> { ChallengeRepositoryImpl(get()) }
    single<PostRepository> { PostRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
}

val dataSourceModule = module {
    single<ChallengeDataSource> { ChallengeDataSourceImpl() }
    single<PostDataSource> { PostDataSourceImpl() }
    single<UserDataSource> { UserDataSourceImpl() }
}

val useCaseModule = module {
    single { GetNewChallengePreviewsUseCase(get()) }
    single { GetChallengingPreviewsUseCase(get()) }
    single { GetChallengeDetailUseCase(get()) }
    single { GetChallengePreviewsByTypeUseCase(get()) }
    single { GetPostUseCase(get()) }
    single { GetUserPostPreviewsUseCase(get()) }
    single { GetUserBadgesUseCase(get()) }
    single { GetUserDetailUseCase(get()) }
}
