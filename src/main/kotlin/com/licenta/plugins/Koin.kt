package com.licenta.plugins

import com.licenta.data.datasources.*
import com.licenta.security.HashingService
import com.licenta.security.HashingServiceImpl
import com.licenta.security.jwt.JwtTokenService
import com.licenta.security.jwt.TokenConfig
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.ktorm.database.Database

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(postureModule)
    }

}

val postureModule = module {
    single {
        Database.connect(
            url= "jdbc:mysql://containers-us-west-82.railway.app:6193/railway",
            user= "root",
            password = System.getenv("DB_PASSWORD")
        )
    }
    single<CaptureDataSource> { CaptureDataSourceImpl(db= get()) }
    single<ExerciseDataSource> { ExerciseDataSourceImpl(db= get()) }
    single<UserDataSource> { UserDataSourceImpl(db= get()) }
    single<WorkoutDataSource> { WorkoutDataSourceImpl(db= get()) }
    single<ExerciseMuscleDataSource> {ExerciseMuscleDataSourceImpl(db= get() )}

    single { JwtTokenService() }
    single {
        TokenConfig(
            issuer = "http://0.0.0.0:5000",
            audience = "users",
            expiresIn = 60L * 60L * 24L * 30L * 1000L, //60sec* 60 min * 24h * 30 days * 1000ms => 30 days in ms
            secret = System.getenv("JWT_SECRET")
        )
    }
    single<HashingService> { HashingServiceImpl() }


}