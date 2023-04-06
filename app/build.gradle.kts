plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.whyranoid.walkie"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.whyranoid.walkie"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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

    implementation(project(":running"))
    implementation(project(":community"))
    implementation(project(":challenge"))
    implementation(project(":mypage"))

    implementation(libs.bundles.android.base)
    implementation(libs.bundles.navigation)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.test.android)

    implementation(libs.hilt)
    kapt(libs.hilt.complier)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
