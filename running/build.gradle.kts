import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())
val naverMapClientId: String = properties.getProperty("naver.map.client.id")

@Suppress("UnstableApiUsage")
android {
    namespace = "com.whyranoid.running"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        manifestPlaceholders["naverMapClientId"] = naverMapClientId
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.bundles.android.base)
    implementation(libs.bundles.navigation)

    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.test.android)

    implementation(libs.naver.maps.android.sdk)
}
