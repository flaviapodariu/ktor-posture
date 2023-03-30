package com.licenta.data.datasources

import com.licenta.data.db.User
import com.licenta.data.db.users
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find

class UserDataSourceImpl(
    db: Database
) : UserDataSource {

    private val users = db.users
    override suspend fun getUserByEmail(email: String) : User? {
        return users.find {it.email eq email}
    }
    override suspend fun createUser(user: User) : Boolean {
        users.add(user)
        return user.id != 0
    }


}