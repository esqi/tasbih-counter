plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("de.mannodermaus.android-junit5") version "1.10.0.0"
}

android {
    namespace = "com.aktiadi.tasbihcounter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aktiadi.tasbihcounter"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
        testInstrumentationRunner="org.junit.runners.JUnit4" // Use JUnit 4 for instrumentation tests
        testInstrumentationRunnerArguments["runnerBuilder"] = "de.mannodermaus.junit5.AndroidJUnit5Builder" // Use JUnit 5 for local unit tests
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}



dependencies {
    implementation("com.google.android.gms:play-services-wearable:18.1.0")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    // https://mvnrepository.com/artifact/androidx.wear/wear-tooling-preview
    implementation("androidx.wear:wear-tooling-preview:1.0.0-rc01")
    implementation("androidx.wear.compose:compose-material:1.2.1")
    implementation("androidx.wear.compose:compose-foundation:1.2.1")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.wear.tiles:tiles:1.2.0")
    implementation("androidx.wear.tiles:tiles-material:1.2.0")
    implementation("com.google.android.horologist:horologist-compose-tools:0.5.10")
    implementation("com.google.android.horologist:horologist-tiles:0.5.10")
    implementation("androidx.wear.watchface:watchface-complications-data-source-ktx:1.1.1")
    testImplementation(kotlin("test"))
    testImplementation("androidx.wear.tiles:tiles-testing:1.3.0-alpha02")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    debugImplementation("androidx.wear.tiles:tiles-renderer:1.3.0-alpha02")
    wearApp(project(":wear"))
}