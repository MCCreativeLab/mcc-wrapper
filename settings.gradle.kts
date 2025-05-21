pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.papermc.io/repository/maven-snapshots/")
    }

    val paperweightVersion: String by settings
    plugins {
        id("io.papermc.paperweight.userdev") version paperweightVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

rootProject.name = "mcc-wrapper"
include("api")
include("vanilla")
include("paper")
include("paper:mod")
include("class-generator")
include("TestSuite")
