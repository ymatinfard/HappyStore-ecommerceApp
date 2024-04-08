plugins {
    alias(libs.plugins.happystore.android.library)
    alias(libs.plugins.happystore.android.hilt)
}

android {
    namespace = "com.matin.happystore.core.redux"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(projects.core.model)
}