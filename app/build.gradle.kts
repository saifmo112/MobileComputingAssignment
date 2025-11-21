// In your build.gradle.kts (app)

// KSP for Room
plugins {
    id("com.google.devtool.ksp") version "1.9.22-1.0.17" // Use your current Kotlin version
}

dependencies {
    // Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // To use Kotlin annotation processing tool (kapt) instead of KSP, use the following:
    // kapt("androidx.room:room-compiler:$room_version")
    // To use KSP:
    ksp("androidx.room:room-compiler:$room_version")
    // Room Kotlin Extensions (Coroutines support)
    implementation("androidx.room:room-ktx:$room_version")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // ViewModel extensions
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
}