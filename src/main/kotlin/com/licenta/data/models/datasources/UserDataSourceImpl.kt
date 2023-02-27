package com.licenta.data.models.datasources

import com.licenta.data.models.User
import com.licenta.data.models.datasources.UserDataSource
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UserDataSourceImpl(
    db:  CoroutineDatabase
): UserDataSource {

    private val users = db.getCollection<User>()
    override suspend fun getUserByEmail(email: String): User? {
        return users.findOne(User::email eq email)
    }
    override suspend fun createUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }
}