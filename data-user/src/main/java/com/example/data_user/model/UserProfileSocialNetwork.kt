package com.example.data_user.model

import com.example.domain_user.model.UserProfileSocialNetworkEntity

data class UserProfileSocialNetwork(
    val title: Int? = null,
    val link: Int? = null
)

fun UserProfileSocialNetwork.toUserProfileSocialNetworkEntity(): UserProfileSocialNetworkEntity {
    return UserProfileSocialNetworkEntity(this.title, this.link)
}