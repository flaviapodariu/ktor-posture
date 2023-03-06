package com.licenta.data.models.db

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.float
import org.ktorm.schema.int
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
sealed interface Capture: Entity<Capture> {
    @Serializable
    companion object : Entity.Factory<Capture>()
    val id: Int
    var user: User
    var headForward: Float
    var lordosis: Float
    var roundedShoulders: Float
    var date: LocalDate
}

object Captures : Table<Capture>("captures") {
    val id = int("id").primaryKey().bindTo { it.id }
    val userId = int("userId").references(Users) { it.user }
    val headForward = float("headForward").bindTo { it.headForward }
    val lordosis = float("lordosis").bindTo { it.lordosis }
    val roundedShoulders = float("roundedShoulders").bindTo { it.roundedShoulders }
    val date = date("date").bindTo { it.date }
}

val Database.captures get() = this.sequenceOf(Captures)

object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        val result = value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        encoder.encodeString(result)
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString())
    }
}