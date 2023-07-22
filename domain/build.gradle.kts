plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.javax.inject)

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}
