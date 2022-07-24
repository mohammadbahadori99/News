package com.example.domain_user.model

data class UserProfileEntity(
    val image: Int? = null,
    val fullName: Int? = null,
    val socialNetwork: List<UserProfileSocialNetworkEntity> = emptyList()
)
