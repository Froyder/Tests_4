package com.geekbrains.tests.di

import com.geekbrains.tests.SchedulerProvider
import com.geekbrains.tests.SearchSchedulerProvider
import com.geekbrains.tests.presenter.RepositoryContract
import com.geekbrains.tests.repository.ApiHolder
import com.geekbrains.tests.repository.FakeGitHubRepository
import com.geekbrains.tests.repository.GitHubRepository
import com.geekbrains.tests.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val main = module {
    single { ApiHolder() }
    single <RepositoryContract> { GitHubRepository(gitHubApi = get())}
    single <SchedulerProvider> { SearchSchedulerProvider()}
    viewModel { SearchViewModel (repository = get()) }
    single { FakeGitHubRepository()}
}

val testModule = module {
    single { ApiHolder() }
    single <RepositoryContract> { GitHubRepository(gitHubApi = get())}
    single <SchedulerProvider> { SearchSchedulerProvider()}
    viewModel { SearchViewModel (repository = get()) }
    single { FakeGitHubRepository()}
}