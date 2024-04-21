plugins {
    alias(libs.plugins.happystore.android.library)
    alias(libs.plugins.happystore.android.hilt)
    alias(libs.plugins.happystore.android.room)
}

android {
    namespace = "com.matin.happystore.core.database"
}

dependencies {

    implementation(projects.core.model)
    implementation(projects.core.common)
}
