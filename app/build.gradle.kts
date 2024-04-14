import com.matin.happystore.HappyStoreBuildType

plugins {
    alias(libs.plugins.happystore.android.application)
    alias(libs.plugins.happystore.android.application.compose)
    alias(libs.plugins.happystore.android.hilt)
    alias(libs.plugins.firebase)
}

android {
    namespace = "com.matin.happystore"

    defaultConfig {
        applicationId = "com.matin.happystore"
        versionCode = 6
        versionName = "2.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = HappyStoreBuildType.DEBUG.appIdSuffix
        }
        release {
            isMinifyEnabled = true
            applicationIdSuffix = HappyStoreBuildType.RELEASE.appIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(projects.feature.products)
    implementation(projects.feature.cart)
    implementation(projects.feature.profile)

    implementation(projects.core.ui)
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.data)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    ksp(libs.hilt.compiler)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(kotlin("test"))
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.tooling)

}