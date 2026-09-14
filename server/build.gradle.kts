// Chemin : server/build.gradle.kts
plugins {
    kotlin("jvm")
    application
    id("com.gradleup.shadow") version "8.3.0"
}

application {
    // La classe principale qui contient ta fonction main()
    mainClass.set("com.iliesnz.server.MainServerKt")
}

dependencies {
    // 🔗 On relie le module shared au serveur
    implementation(project(":shared"))

    // Dépendances nécessaires au serveur
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
    implementation("com.google.code.gson:gson:2.14.0")
    testImplementation(libs.junit.junit)
}
