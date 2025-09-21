plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.speedmonitor"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.speedmonitor"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.6.0"
    }

    kotlinOptions {
        jvmTarget = "21"
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(platform("com.google.firebase:firebase-bom:34.2.0"))
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.amazonaws:aws-android-sdk-sns:2.48.0")
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("androidx.navigation:navigation-compose:2.7.0")
}
