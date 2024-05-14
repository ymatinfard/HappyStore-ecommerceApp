plugins {
    alias(libs.plugins.happystore.android.feature)
    alias(libs.plugins.happystore.android.library.compose)
}

android {
    namespace = "com.matin.happystore.feature.map"
}

dependencies {
    implementation(libs.google.map.compose)
    implementation(projects.core.common)
    implementation(libs.play.services.location)
    implementation(projects.core.ui)
}
