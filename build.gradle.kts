val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val kMongoVersion: String by project
val commonsCodecVersion: String by project
val mySqlVersion: String by project
val ktormVersion: String by project
val koin_ktor: String by project

plugins {
    kotlin("jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.3.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"

}

group = "com.licenta"
version = "0.0.1"
application {
    mainClass.set("com.licenta.ApplicationKt")

    val isDevelopment: Boolean = false
//    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.licenta.ApplicationKt"
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("io.ktor:ktor-server-swagger:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("commons-codec:commons-codec:$commonsCodecVersion")
    implementation("org.litote.kmongo:kmongo:$kMongoVersion")
    implementation("org.litote.kmongo:kmongo-coroutine:$kMongoVersion")
    implementation("io.ktor:ktor-server-openapi:$ktorVersion")
    // Koin for Ktor
    implementation("io.insert-koin:koin-ktor:$koin_ktor")
    // SLF4J Logger
    implementation( "io.insert-koin:koin-logger-slf4j:$koin_ktor")

    // https://mvnrepository.com/artifact/mysql/mysql-connector-java
    implementation("mysql:mysql-connector-java:$mySqlVersion")
    implementation("org.ktorm:ktorm-core:$ktormVersion")
    implementation("org.ktorm:ktorm-support-mysql:$ktormVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

}