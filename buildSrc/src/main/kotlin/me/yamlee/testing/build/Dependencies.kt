package me.yamlee.testing.build

object Deps {

    object Versions {

        object Android {
            const val compileSdkVersion = 33
            const val minSdkVersion = 23
            const val targetSdkVersion = 33
        }

        const val kotlin = "1.5.31"

    }

    object AndroidX {
        const val core_ktx = "androidx.core:core-ktx:1.9.0"
        const val appcompat = "androidx.appcompat:appcompat:1.6.1"
        const val material = "com.google.android.material:material:1.8.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    }

    object Test {
        const val androidXTestCore = "androidx.test:core:1.4.0"
        const val androidXTruthExt = "androidx.test.ext:truth:1.4.0"
        const val androidXJUnitExt = "androidx.test.ext:junit:1.1.3"
        const val androidXEspressoCore = "androidx.test.espresso:espresso-core:3.4.0"

        const val junit = "junit:junit:4.12"
        const val mockito = "org.mockito:mockito-core:3.6.28"
        const val truth = "com.google.truth:truth:1.1.3"
        const val robolectric = "org.robolectric:robolectric:4.9"
        const val guavaAndroid = "com.google.guava:guava:28.0-android"
        const val mockKotlin = "io.mockk:mockk:1.12.3"
        const val mockOnline = "org.mockito:mockito-inline:3.8.0"
    }
}