import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val logbackclassic_version: String by project
val bcrypt_version: String by project
val koin_ksp_version: String by project
val koin_ktor_version: String by project
val micrologging_version: String by project
val junit_version: String by project

plugins {
    kotlin("jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.2.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
    id("org.jetbrains.dokka") version "1.7.20"
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
    implementation("io.ktor:ktor-client-json:$ktor_version")

    // Auth JWT
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")

    // JSON content negotiation
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    // Content validation
    implementation("io.ktor:ktor-server-request-validation:$ktor_version")

    // Koin - Core
    implementation("io.insert-koin:koin-core:3.2.2")

    // Koin Anotaciones
    implementation("io.insert-koin:koin-annotations:1.0.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.2.3")
    implementation("io.ktor:ktor-serialization-gson:2.2.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    ksp("io.insert-koin:koin-ksp-compiler:1.0.3")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logbackclassic_version")
    implementation("io.github.microutils:kotlin-logging-jvm:$micrologging_version")

    // Mongo Reactivo
    implementation("org.litote.kmongo:kmongo-async:4.7.2")
    implementation("org.litote.kmongo:kmongo-coroutine:4.7.2")

    // Serializar KMongo
    implementation("org.litote.kmongo:kmongo-id-serialization:4.1.3")

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
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junit_version")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit_version")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("io.mockk:mockk:1.13.2")
    implementation("io.ktor:ktor-client-json:$ktor_version")
    implementation("com.google.code.gson:gson:2.8.7")

}

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

tasks.test {
    useJUnitPlatform()
}
