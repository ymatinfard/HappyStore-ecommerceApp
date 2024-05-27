plugins {
    alias(libs.plugins.happystore.android.library)
    alias(libs.plugins.happystore.android.library.compose)
}

android {
    namespace = "com.matin.happystore.core.designsystem"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material3)
    api(libs.coil.kt.compose)
    implementation(libs.androidx.ui.text.google.fonts)
}
