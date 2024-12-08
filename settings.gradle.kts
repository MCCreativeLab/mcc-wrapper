pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

rootProject.name = "mcc-wrapper"
include("mcc-class-generator")
include("api")
include("vanilla")
include("paper")
