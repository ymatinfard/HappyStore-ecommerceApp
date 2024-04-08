plugins {
    alias(libs.plugins.happystore.android.library)
    alias(libs.plugins.happystore.android.hilt)
}

android {
    namespace = "com.matin.happystore.core.domain"
}

dependencies {

    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.common)
}