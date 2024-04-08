plugins {
    alias(libs.plugins.happystore.android.library.compose)
    alias(libs.plugins.happystore.android.feature)
}

android {
    namespace = "com.matin.happystore.feature.products"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.redux)
    implementation(projects.core.model)
    implementation(projects.core.common)
}