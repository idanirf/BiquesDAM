val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val logbackclassic_version: String by project
val bcrypt_version: String by project
val koin_ksp_version: String by project
val koin_ktor_version: String by project
val micrologging_version: String by project

plugins {
    kotlin("jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.2.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
}

group = "biques.dam.es"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor Core
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")

    // Auth JWT
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")

    // JSON content negotiation
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    // Content validation
    implementation("io.ktor:ktor-server-request-validation:$ktor_version")

    // Caching Headers
    implementation("io.ktor:ktor-server-caching-headers-jvm:$ktor_version")

    // Compression
    implementation("io.ktor:ktor-server-compression-jvm:$ktor_version")
    // CORS
    implementation("io.ktor:ktor-server-cors-jvm:$ktor_version")

    // WebSockets
    implementation("io.ktor:ktor-server-websockets-jvm:$ktor_version")

    // Swagger
    implementation("io.ktor:ktor-server-swagger:$ktor_version")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logbackclassic_version")
    implementation("io.github.microutils:kotlin-logging-jvm:$micrologging_version")

    // Koin
    implementation("io.insert-koin:koin-ktor:$koin_ktor_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_ktor_version")
    implementation("io.insert-koin:koin-annotations:$koin_ksp_version")
    ksp("io.insert-koin:koin-ksp-compiler:$koin_ksp_version")

    // Bcrypt
    implementation("org.mindrot:jbcrypt:$bcrypt_version")

    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}