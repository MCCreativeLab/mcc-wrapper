### Setup ###
```kotlin
repositories {
    maven("https://repo.verdox.de/snapshots")
}
dependencies {
    // If you want the latest version use '+'. Else you must specify a version.
    implementation("de.verdox.mccreativelab.mcc-wrapper:api:+")     // If you want to use the api
    implementation("de.verdox.mccreativelab.mcc-wrapper:vanilla:+") // If you want to use the vanilla implementation based on nms
    implementation("de.verdox.mccreativelab.mcc-wrapper:paper:+")   // If you want to use the paper implementation based on the vanilla implementation
}

```
