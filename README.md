### Setup ###
```kotlin
repositories {
    maven("https://repo.verdox.de/snapshots")
}
dependencies {
    // If you want the latest version use '+'. Else you must specify a version.
    implementation("de.verdox.mccreativelab.mcc-wrapper:api:1.21.4-R0.1-SNAPSHOT")     // If you want to use the api
    implementation("de.verdox.mccreativelab.mcc-wrapper:vanilla:1.21.4-R0.1-SNAPSHOT") // If you want to use the vanilla implementation based on nms
    implementation("de.verdox.mccreativelab.mcc-wrapper:paper:1.21.4-R0.1-SNAPSHOT")   // If you want to use the paper implementation based on the vanilla implementation
}

```
