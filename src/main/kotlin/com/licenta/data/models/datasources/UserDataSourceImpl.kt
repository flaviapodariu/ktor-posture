package com.licenta.data.models.datasources

import com.licenta.data.models.db.User
import com.licenta.data.models.db.users
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.all
import org.ktorm.entity.find

class UserDataSourceImpl(
    db: Database
): UserDataSource {

    private val users = db.users
    override suspend fun getUserByEmail(email: String): User? {
        return users.find {it.email eq email}
    }
    override suspend fun createUser(user: User): Boolean {
        users.add(user)
        return user.id != 0
    }
}