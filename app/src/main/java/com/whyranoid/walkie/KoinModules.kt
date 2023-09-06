package com.whyranoid.walkie

import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.whyranoid.data.API
import com.whyranoid.data.AccountDataStore
import com.whyranoid.data.AppDatabase
import com.whyranoid.data.datasource.ChallengeDataSourceImpl
import com.whyranoid.data.datasource.PostDataSourceImpl
import com.whyranoid.data.datasource.UserDataSourceImpl
import com.whyranoid.data.datasource.account.AccountDataSourceImpl
import com.whyranoid.data.datasource.account.AccountService
import com.whyranoid.data.repository.AccountRepositoryImpl
import com.whyranoid.data.repository.ChallengeRepositoryImpl
import com.whyranoid.data.repository.PostRepositoryImpl
import com.whyranoid.data.repository.RunningHistoryRepositoryImpl
import com.whyranoid.data.repository.RunningRepositoryImpl
import com.whyranoid.data.repository.UserRepositoryImpl
import com.whyranoid.domain.datasource.AccountDataSource
import com.whyranoid.domain.datasource.ChallengeDataSource
import com.whyranoid.domain.datasource.PostDataSource
import com.whyranoid.domain.datasource.UserDataSource
import com.whyranoid.domain.repository.AccountRepository
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
import com.whyranoid.domain.usecase.SignOutUseCase
import com.whyranoid.domain.usecase.running.GetRunningFollowerUseCase
import com.whyranoid.domain.usecase.running.RunningFinishUseCase
import com.whyranoid.domain.usecase.running.RunningStartUseCase
import com.whyranoid.presentation.screens.mypage.editprofile.EditProfileViewModel
import com.whyranoid.presentation.viewmodel.ChallengeDetailViewModel
import com.whyranoid.presentation.viewmodel.ChallengeExitViewModel
import com.whyranoid.presentation.viewmodel.ChallengeMainViewModel
import com.whyranoid.presentation.viewmodel.RunningEditViewModel
import com.whyranoid.presentation.viewmodel.RunningViewModel
import com.whyranoid.presentation.viewmodel.SelectHistoryViewModel
import com.whyranoid.presentation.viewmodel.SignInViewModel
import com.whyranoid.presentation.viewmodel.SplashViewModel
import com.whyranoid.presentation.viewmodel.UserPageViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    single { ChallengeMainViewModel(get(), get(), get()) }
    single { ChallengeDetailViewModel(get()) }
    single { ChallengeExitViewModel(get()) }
    single { UserPageViewModel(get(), get(), get(), get(), get()) }
    factory { RunningViewModel(get(), get(), get(), get(), get()) }
    factory { RunningEditViewModel() }
    factory { SplashViewModel(get()) }
    factory { SignInViewModel(get()) }
    factory { SelectHistoryViewModel(get()) }
    factory { EditProfileViewModel(get()) }
}

val repositoryModule = module {
    single<ChallengeRepository> { ChallengeRepositoryImpl(get()) }
    single<PostRepository> { PostRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<RunningRepository> { RunningRepositoryImpl(get()) }
    single<RunningHistoryRepository> { RunningHistoryRepositoryImpl(get(), get()) }
    single<AccountRepository> { AccountRepositoryImpl(get(), get()) }
}

val dataSourceModule = module {
    single<ChallengeDataSource> { ChallengeDataSourceImpl() }
    single<PostDataSource> { PostDataSourceImpl() }
    single<UserDataSource> { UserDataSourceImpl(get()) }
    single<AccountDataSource> { AccountDataSourceImpl(get()) }
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
    single { RunningStartUseCase() }
    single { SignOutUseCase(get()) }
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

val networkModule = module {
    class WalkieInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val newRequest =
                chain.request().newBuilder().header("Content-Type", "application/json")
                    .build()
            return chain.proceed(newRequest)
        }
    }

    single {
        OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(WalkieInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            )
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(API.BASE_URL)
            .client(get())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create(),
                ),
            )
            .build()
    }

    single {
        get<Retrofit>().create(AccountService::class.java)
    }
}
