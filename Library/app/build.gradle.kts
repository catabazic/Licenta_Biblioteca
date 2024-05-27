val implementation: Unit
    get() {
        TODO()
    }

plugins {
    alias(libs.plugins.androidApplication)
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
}

//chaquopy {
//    defaultConfig {
//        version = "3.8"
//        buildPython("C:\\py\\python.exe")
//        buildPython("C:\\Windows\\py.exe", "-3.8")
//
//        pyc {
//            src = false
//        }
//        pip {
//            install ("pandas")
//            install ("scikit-learn")
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
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}