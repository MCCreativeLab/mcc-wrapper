plugins {
    id("java")
    `maven-publish`
    id("io.papermc.paperweight.userdev") apply false
    id("xyz.jpenilla.run-paper") version "2.3.1" apply false // Adds runServer and runMojangMappedServer tasks for testing
    id("fabric-loom") version "1.9-SNAPSHOT" apply false
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-releases/")
}

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "java")

    repositories {
        mavenCentral()
        mavenLocal()
        maven {
            name = "Verdox Reposilite"
            url = uri("https://repo.verdox.de/snapshots")
        }
        maven("https://papermc.io/repo/repository/maven-releases/")
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
        withSourcesJar()
        //withJavadocJar()
    }

    dependencies {
        compileOnly("net.kyori:adventure-api:4.17.0")
        compileOnly("de.verdox:vserializer:+")
        compileOnly("it.unimi.dsi:fastutil:8.5.15")
        implementation("com.google.guava:guava:33.3.1-jre")
        testImplementation("de.verdox:vserializer:+")
    }

    publishing {
        repositories {
            maven {
                name = "verdox"
                url = uri("https://repo.verdox.de/snapshots")
                credentials {
                    username = (findProperty("reposilite.verdox.user") ?: System.getenv("REPO_USER")).toString()
                    password = (findProperty("reposilite.verdox.key") ?: System.getenv("REPO_PASSWORD")).toString()
                }
            }
        }
    }
}

dependencies {
    compileOnly("net.kyori:adventure-api:4.17.0")
    compileOnly("it.unimi.dsi:fastutil:8.5.15")
    //compileOnly("com.mojang:authlib:1.5.25")
    implementation("com.google.guava:guava:33.3.1-jre")
    implementation("net.bytebuddy:byte-buddy:1.15.10")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains:annotations:26.0.1")
    testImplementation("org.ow2.asm:asm-tree:9.7")
    testImplementation("com.google.guava:guava:33.3.1-jre")
    testImplementation("org.mockito:mockito-core:5.14.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21)) // oder 17, falls gewünscht
    }
}

tasks.test {
    useJUnitPlatform()
}
