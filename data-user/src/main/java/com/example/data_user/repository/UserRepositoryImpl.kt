package com.example.data_user.repository

import com.example.data_user.datasource.local.UserLocalDataSource
import com.example.data_user.model.toUserProfileEntity
import com.example.domain_user.model.UserProfileEntity
import com.example.domain_user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
    override fun getUserProfileLocal(): Flow<UserProfileEntity> {
        return userLocalDataSource.getUserProfile().map { it.toUserProfileEntity() }
    }
}
