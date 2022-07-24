package com.example.data_user.model

import com.example.domain_user.model.UserProfileEntity

data class UserProfile(
    val image: Int? = null,
    val fullName: Int? = null,
    val socialNetwork: List<UserProfileSocialNetwork> = emptyList()
)

fun UserProfile.toUserProfileEntity(): UserProfileEntity {
    return UserProfileEntity(
        this.image,
        this.fullName,
        this.socialNetwork.map { it.toUserProfileSocialNetworkEntity() })
}