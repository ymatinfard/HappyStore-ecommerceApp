plugins {
    alias(libs.plugins.happystore.android.library)
    alias(libs.plugins.happystore.android.library.compose)
    alias(libs.plugins.happystore.android.hilt)
}

android {
    namespace = "com.matin.happystore.core.testing"
}

dependencies {

    api(kotlin("test"))
    api(libs.androidx.compose.ui.test)

    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.common)

    implementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(libs.androidx.junit)
}
