plugins {
    alias(libs.plugins.happystore.android.library)
}

android {
    namespace = "com.matin.happystore.core.common"
}

dependencies {

    implementation(libs.androidx.core.ktx)
}