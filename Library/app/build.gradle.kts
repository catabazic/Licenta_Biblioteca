val implementation: Unit
    get() {
        TODO()
    }

val DependencyHandlerScope.annotationProcessor: Unit
    get() {
        TODO("Not yet implemented")
    }

plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
//    alias(libs.plugins.chaquopy)
}

android {
    namespace = "com.example.library"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.library"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        ndk {
//            abiFilters += listOf("arm64-v8a", "x86_64")
//        }
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

    packaging {
        resources {
            excludes += listOf("META-INF/NOTICE.md", "META-INF/LICENSE.md")
        }
    }
}

//chaquopy {
//    defaultConfig {
//        buildPython("C:\\py\\python.exe")
//        buildPython("C:\\Windows\\py.exe", "-3.12")
//
//        pyc {
//            src = false
//        }
//    }
//    productFlavors { }
//    sourceSets {
//        getByName("main") {
//            srcDir("src/main/python")
//        }
//    }
//}


dependencies {
    implementation(libs.appcompat);
    implementation(libs.material);
    implementation(libs.activity);
    implementation(libs.constraintlayout);
    implementation(libs.jakarta.mail)
    implementation(libs.jakarta.mail.activation)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    testImplementation(libs.junit);
    androidTestImplementation(libs.ext.junit);
    androidTestImplementation(libs.espresso.core);
}