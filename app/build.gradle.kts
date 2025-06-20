plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    //id("com.google.devtools.ksp") version "2.1.0-1.0.29"
}

android {
    namespace = "com.battlestats.wartracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.battlestats.wartracker"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.material3)
    implementation(libs.liveData)
    implementation(libs.composableNavigation)
    implementation(libs.coil.compose)
    implementation(libs.coil.network)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //koin
    implementation (libs.koin.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.test)
    implementation(libs.koin.test.junit4)
    implementation(libs.koin.androidx.navigation)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // OkHttp (for logging)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Gson
    implementation(libs.gson)
    implementation(libs.androidx.foundation.layout.android)

    // Room

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    //ksp(libs.room.compiler)
    //ksp(libs.ksp)








    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}