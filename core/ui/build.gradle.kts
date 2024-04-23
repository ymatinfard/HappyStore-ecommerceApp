plugins {
    alias(libs.plugins.happystore.android.library)
    alias(libs.plugins.happystore.android.library.compose)
}

android {
    namespace = "com.matin.happystore.core.ui"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

    api(projects.core.designsystem)
    api(projects.core.model)
    implementation(libs.javax.inject)
}
