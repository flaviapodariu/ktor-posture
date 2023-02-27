package com.licenta.data.models.datasources

import com.licenta.data.models.User

interface UserDataSource {

    suspend fun getUserByEmail(email: String) : User?
    suspend fun createUser(user: User): Boolean
}