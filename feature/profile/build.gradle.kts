plugins {
    alias(libs.plugins.happystore.android.feature)
    alias(libs.plugins.happystore.android.library.compose)
}

android {
    namespace = "com.matin.happystore.feature.profile"
}

dependencies {

    implementation(projects.core.ui)
}