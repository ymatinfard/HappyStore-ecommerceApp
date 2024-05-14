plugins {
    alias(libs.plugins.happystore.android.library)
    alias(libs.plugins.happystore.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.matin.happystore.core.network"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.serialization.json)
}
