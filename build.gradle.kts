// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0-rc02" apply false
    id("org.jetbrains.kotlin.android") version "2.0.20" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20" apply false
    id("com.google.dagger.hilt.android") version "2.52" apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.25" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20" apply false
    id("org.jetbrains.kotlin.jvm") version "2.0.20" apply false
}

buildscript {
    dependencies {
        classpath(kotlin("gradle-plugin", version = "2.0.20"))
        classpath(kotlin("serialization", version = "2.0.20"))
    }
}

