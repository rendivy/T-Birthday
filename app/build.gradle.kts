plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "ru.yangel.hackathon"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.yangel.hackathon"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val work_version = "2.9.0"
    val room_version = "2.6.1"
    val koinVersion = "3.5.6"
    val navVersion = "2.7.7"
    val calendarVersion = "2.5.0"
    val loggingInterceptorVersion = "4.11.0"
    
    val koinCore = "io.insert-koin:koin-core:$koinVersion"
    val koinAndroid = "io.insert-koin:koin-android:$koinVersion"
    val koinAndroidCompose = "io.insert-koin:koin-androidx-compose:$koinVersion"
    val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$loggingInterceptorVersion"
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("androidx.work:work-runtime-ktx:$work_version")
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("io.insert-koin:koin-androidx-workmanager:$koinVersion")
    implementation("com.kizitonwose.calendar:compose:$calendarVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation(koinCore)
    implementation(koinAndroidCompose)
    implementation(koinAndroid)
    implementation(loggingInterceptor)
    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.retrofit.adapters.result)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    ksp("androidx.room:room-compiler:$room_version")
    implementation(libs.coil.compose)
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.converter.gson)
}