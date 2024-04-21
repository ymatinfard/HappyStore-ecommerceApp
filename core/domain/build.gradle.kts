plugins {
    alias(libs.plugins.happystore.android.library)
    alias(libs.plugins.happystore.android.hilt)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.matin.happystore.core.domain"
}

dependencies {

    api(projects.core.data)
    api(projects.core.model)
    implementation(projects.core.common)
    implementation(libs.javax.inject)
}
