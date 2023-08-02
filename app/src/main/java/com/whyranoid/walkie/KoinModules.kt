package com.whyranoid.walkie

import androidx.room.Room
import com.google.gson.Gson
import com.whyranoid.data.AccountDataStore
import com.whyranoid.data.AppDatabase
import com.whyranoid.data.datasource.ChallengeDataSourceImpl
import com.whyranoid.data.datasource.PostDataSourceImpl
import com.whyranoid.data.datasource.UserDataSourceImpl
import com.whyranoid.data.repository.ChallengeRepositoryImpl
import com.whyranoid.data.repository.PostRepositoryImpl
import com.whyranoid.data.repository.RunningHistoryRepositoryImpl
import com.whyranoid.data.repository.RunningRepositoryImpl
import com.whyranoid.data.repository.UserRepositoryImpl
import com.whyranoid.domain.datasource.ChallengeDataSource
import com.whyranoid.domain.datasource.PostDataSource
import com.whyranoid.domain.datasource.UserDataSource
import com.whyranoid.domain.repository.ChallengeRepository
import com.whyranoid.domain.repository.PostRepository
import com.whyranoid.domain.repository.RunningHistoryRepository
import com.whyranoid.domain.repository.RunningRepository
import com.whyranoid.domain.repository.UserRepository
import com.whyranoid.domain.usecase.GetChallengeDetailUseCase
import com.whyranoid.domain.usecase.GetChallengePreviewsByTypeUseCase
import com.whyranoid.domain.usecase.GetChallengingPreviewsUseCase
import com.whyranoid.domain.usecase.GetNewChallengePreviewsUseCase
import com.whyranoid.domain.usecase.GetPostUseCase
import com.whyranoid.domain.usecase.GetUserBadgesUseCase
import com.whyranoid.domain.usecase.GetUserDetailUseCase
import com.whyranoid.domain.usecase.GetUserPostPreviewsUseCase
import com.whyranoid.domain.usecase.running.GetRunningFollowerUseCase
import com.whyranoid.domain.usecase.running.RunningFinishUseCase
import com.whyranoid.domain.usecase.running.RunningPauseOrResumeUseCase
import com.whyranoid.domain.usecase.running.RunningStartUseCase
import com.whyranoid.presentation.viewmodel.ChallengeDetailViewModel
import com.whyranoid.presentation.viewmodel.ChallengeExitViewModel
import com.whyranoid.presentation.viewmodel.ChallengeMainViewModel
import com.whyranoid.presentation.viewmodel.RunningEditViewModel
import com.whyranoid.presentation.viewmodel.RunningViewModel
import com.whyranoid.presentation.viewmodel.UserPageViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val viewModelModule = module {
    single { ChallengeMainViewModel(get(), get(), get()) }
    single { ChallengeDetailViewModel(get()) }
    single { ChallengeExitViewModel(get()) }
    single { UserPageViewModel(get(), get(), get(), get()) }
    factory { RunningViewModel(get(), get(), get(), get(), get(), get()) }
    factory { RunningEditViewModel() }
}

val repositoryModule = module {
    single<ChallengeRepository> { ChallengeRepositoryImpl(get()) }
    single<PostRepository> { PostRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<RunningRepository> { RunningRepositoryImpl(get()) }
    single<RunningHistoryRepository> { RunningHistoryRepositoryImpl(get(), get()) }
}

val dataSourceModule = module {
    single<ChallengeDataSource> { ChallengeDataSourceImpl() }
    single<PostDataSource> { PostDataSourceImpl() }
    single<UserDataSource> { UserDataSourceImpl(get()) }
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
    single { GetRunningFollowerUseCase() }
    single { RunningFinishUseCase() }
    single { RunningPauseOrResumeUseCase() }
    single { RunningStartUseCase() }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "walkie_database",
        ).build()
    }

    single {
        get<AppDatabase>().runningHistoryDao()
    }

    single { Gson() }

    single { AccountDataStore(get()) }
}
