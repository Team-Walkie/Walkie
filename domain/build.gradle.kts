plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation(libs.javax.inject)

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    val pagingVersion = "3.2.1"
    implementation("androidx.paging:paging-common:$pagingVersion")

}
