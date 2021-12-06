package com.geekbrains.tests.di

import com.geekbrains.tests.repository.ApiHolder
import com.geekbrains.tests.repository.FakeGitHubRepository
import com.geekbrains.tests.repository.GitHubRepository
import org.koin.dsl.module

val repository = module {
    single { ApiHolder() }
    single { GitHubRepository(gitHubApi = get())}
    single { FakeGitHubRepository()}
}