plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.pokemonapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pokemonapp"
        minSdk = 24
        targetSdk = 34
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

    viewBinding.isEnabled = true
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation ("com.google.android.material:material:1.9.0")


    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.1")  // Usa la versión correspondiente
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.1")

    // Hilt

    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation(libs.core.ktx)
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    // Hilt ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1") // Para ViewModel y LiveData

    // Coroutines & Flow
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2")

    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")

    // Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Gson Converter

    // Coil para imágenes
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Accompanist para permisos
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")

    // Lifecycle para ViewModel y LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // WorkManager para modo offline
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    implementation ("de.hdodenhof:circleimageview:3.1.0")

    implementation("androidx.recyclerview:recyclerview:1.3.1")

    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")
    implementation ("org.mindrot:jbcrypt:0.4") // Para usar BCrypt


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}