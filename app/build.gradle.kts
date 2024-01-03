plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")

    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.financetrackerapp_ctis487_team6"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.financetrackerapp_ctis487_team6"
        minSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding=true
    }
}

dependencies {

    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    //STEP1: Include retrofit and converter
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson Converter
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("com.daimajia.androidanimations:library:2.4@aar")

    //Alternative converters: Moshi Converter
    //implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")

    // Alternative converters: Kotlinx Serialization Converter
    //implementation ("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    //Alternative converters: Jackson Converter
    //implementation ("com.squareup.retrofit2:converter-jackson:2.9.0")

    val room_version = "2.6.0"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")

    val worker_version="2.7.1"
    implementation ("androidx.work:work-runtime:$worker_version")

    implementation("com.google.firebase:firebase-analytics")
}