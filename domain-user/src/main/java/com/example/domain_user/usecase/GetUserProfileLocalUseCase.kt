package com.example.domain_user.usecase

import com.example.domain_user.repository.UserRepository
import javax.inject.Inject

class GetUserProfileLocalUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.getUserProfileLocal()
}
