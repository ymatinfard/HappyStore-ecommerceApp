plugins {
    alias(libs.plugins.happystore.android.feature)
    alias(libs.plugins.happystore.android.library.compose)
}

android {
    namespace = "com.matin.happystore.feature.search"
}

dependencies {

    implementation(projects.core.ui)
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.testing)
}