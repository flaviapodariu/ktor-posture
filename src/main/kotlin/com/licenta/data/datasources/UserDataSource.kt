package com.licenta.data.datasources

import com.licenta.data.db.User

interface UserDataSource {

    suspend fun getUserByEmail(email: String) : User?
    suspend fun createUser(user: User) : Boolean

}