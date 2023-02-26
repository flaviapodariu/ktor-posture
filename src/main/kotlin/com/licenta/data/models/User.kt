package com.licenta.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
data class User(
    @BsonId
    val id: ObjectId = ObjectId(),
    val email: String,
    val nickname: String,
    val password: String,
    val salt: String
)
