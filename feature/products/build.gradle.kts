plugins {
    alias(libs.plugins.happystore.android.feature)
}

android {
    namespace = "com.matin.happystore.feature.products"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}