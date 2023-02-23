import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

group = "es.dam.biques"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")

    // Spring Security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // H2 Database Reactive
    runtimeOnly("io.r2dbc:r2dbc-h2")
    // H2 Database
    // runtimeOnly("com.h2database:h2")

    // Dependencias de Spring Data Reactive
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    // Dependencias de Spring Data NO REACTIVO.
    // implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Spring Caché
    implementation("org.springframework.boot:spring-boot-starter-cache")

    // Spring Validador
    implementation("org.springframework.boot:spring-boot-starter-validation")

    //Spring WebSocket
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    // Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

    // Loggers
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

    // JWT
    implementation("com.auth0:java-jwt:4.2.1")

    // Bcrypt
    implementation("org.mindrot:jbcrypt:0.4")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")

    // Corrutinas
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    // Mockk
    testImplementation("com.ninja-squad:springmockk:4.0.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
