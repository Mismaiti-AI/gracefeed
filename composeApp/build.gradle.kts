import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.google.services)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.androidx.room.sqlite.wrapper)

            implementation(libs.koin.androidx.compose)

            // [auth] start
            implementation(libs.androidx.credentials)
            implementation(libs.androidx.credentials.play.services.auth)
            implementation(libs.googleid)
            // [auth] end

            // [media_capture] start
            implementation(libs.androidx.exifinterface)
            // [media_capture] end
            // [video_playback] start
            implementation(libs.androidx.media3.exoplayer)
            implementation(libs.androidx.media3.ui)
            // [video_playback] end
            implementation(libs.google.maps.compose)
            implementation(libs.google.play.services.maps)

            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.crashlytics)
            // [push_notifications]
            implementation(libs.firebase.messaging)
            implementation(libs.firebase.firestore)
            // [messaging]
            implementation(libs.firebase.database)
        

        // Video player (Media3/ExoPlayer)
        implementation("androidx.media3:media3-exoplayer:1.2.1")
        implementation("androidx.media3:media3-ui-compose:1.2.1")
        implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

        // Google Maps
        implementation("com.google.android.gms:play-services-maps:18.2.0")

        // Firebase Firestore
        implementation("com.google.firebase:firebase-firestore-ktx")}

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.materialIconsExtended)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.ui.toolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.kermit)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.serialization.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.coil)
            implementation(libs.coil.network.ktor)
            implementation(libs.coil.compose)
            implementation(libs.coil.mp)
            implementation(libs.coil.gif)

            implementation(libs.kotlinx.datetime)

            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.materialkolor)

            // [firebase_auth]
            implementation(libs.gitlive.firebase.auth)
            // [firebase_auth]
            implementation(libs.gitlive.firebase.common)

            // [payment] start
            implementation(libs.purchases.kmp.core)
            implementation(libs.purchases.kmp.ui)
            // [payment] end
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        // Global opt-in for experimental APIs to avoid annotation errors
        all {
            languageSettings.optIn("kotlin.time.ExperimentalTime")
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
            languageSettings.optIn("androidx.compose.foundation.ExperimentalFoundationApi")
        }
    }
}

android {
    namespace = "com.gracefeed"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.gracefeed"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    signingConfigs {
        getByName("debug") {
            // Uses default debug keystore at ~/.android/debug.keystore
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            // Sign with debug keystore for Firebase App Distribution testing
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.gracefeed"
    generateResClass = always
}
