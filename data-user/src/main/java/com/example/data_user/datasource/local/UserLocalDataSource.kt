package com.example.data_user.datasource.local


import com.example.data_user.R
import com.example.data_user.model.UserProfile
import com.example.data_user.model.UserProfileSocialNetwork
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSource @Inject constructor() {

    fun getUserProfile() = flow {
        emit(
            UserProfile(
                image = R.drawable.image_profile,
                fullName = R.string.label_full_name,
                socialNetwork = ArrayList<UserProfileSocialNetwork>().apply {
                    add(
                        UserProfileSocialNetwork(
                            title = R.string.label_github,
                            link = R.string.link_github
                        )
                    )
                    add(
                        UserProfileSocialNetwork(
                            title = R.string.label_gitlab,
                            link = R.string.link_gitlab
                        )
                    )
                    add(
                        UserProfileSocialNetwork(
                            title = R.string.label_linkedin,
                            link = R.string.link_linkedin
                        )
                    )
                    add(
                        UserProfileSocialNetwork(
                            title = R.string.label_telegram,
                            link = R.string.link_telegram
                        )
                    )
                    add(
                        UserProfileSocialNetwork(
                            title = R.string.label_instagram,
                            link = R.string.link_instagram
                        )
                    )
                }
            )
        )
    }
}
