// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.navigationSafeArgs) apply false
    alias(libs.plugins.ksp.plugin) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}