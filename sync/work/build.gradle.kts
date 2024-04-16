plugins {
    alias(libs.plugins.happystore.android.library)
    alias(libs.plugins.happystore.android.hilt)
}

android {
    namespace = "com.matin.happystore.sync.work"
}

dependencies {
    implementation(projects.core.data)
    implementation(libs.hilt.ext.work)
    implementation(libs.androidx.work.ktx)
    implementation(projects.core.testing)
    implementation(projects.core.common)
    ksp(libs.hilt.ext.compiler)

}