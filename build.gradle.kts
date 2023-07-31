// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    System.setProperty("coroutines_version", "1.7.2")
    System.setProperty("retrofit_version", "2.9.0")
    System.setProperty("room_version", "2.5.2")
    System.setProperty("datastore_version", "1.0.0")
    System.setProperty("lottie_version", "6.1.0")
    System.setProperty("koin_version", "3.4.3")
}
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}