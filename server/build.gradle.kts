// Chemin : server/build.gradle.kts
plugins {
    kotlin("jvm")
    application
}

application {
    // La classe principale qui contient ta fonction main()
    mainClass.set("org.example.MainServerKt")
}

dependencies {
    // 🔗 On relie le module shared au serveur
    implementation(project(":shared"))

    // Dépendances nécessaires au serveur
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("com.google.code.gson:gson:2.14.0")
    testImplementation(libs.junit.junit)
}
