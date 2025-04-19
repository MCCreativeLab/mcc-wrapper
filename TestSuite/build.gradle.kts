plugins {
    id("java")
    id("io.papermc.paperweight.userdev")
    id("xyz.jpenilla.run-paper") version "2.3.1" apply false // Adds runServer and runMojangMappedServer tasks for testing
}

group = "de.verdox.mccreativelab"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle(providers.gradleProperty("version").get())
    runtimeOnly(platform("org.junit:junit-bom:5.10.0"))
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    compileOnly(project(":api"))
    compileOnly("de.verdox.mccreativelab:mcc-pack-generator:" + providers.gradleProperty("version").get())
}

tasks.test {
    useJUnitPlatform()
}