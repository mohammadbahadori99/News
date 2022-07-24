package com.example.domain_user.repository

import com.example.domain_user.model.UserProfileEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository  {
    fun getUserProfileLocal() : Flow<UserProfileEntity>
}
