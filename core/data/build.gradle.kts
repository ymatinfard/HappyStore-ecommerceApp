plugins {
    alias(libs.plugins.happystore.android.library)
    alias(libs.plugins.happystore.android.hilt)
}

android {
    namespace = "com.matin.happystore.core.data"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(projects.core.network)
    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(projects.core.database)
    implementation(projects.core.testing)
}