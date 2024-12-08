plugins {
    id("java")
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "1.7.3"
    id("xyz.jpenilla.run-paper") version "2.3.1" apply false // Adds runServer and runMojangMappedServer tasks for testing
}

repositories {
    mavenCentral()
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
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
        withSourcesJar()
        withJavadocJar()
    }

    dependencies {
        compileOnly("net.kyori:adventure-api:4.17.0")
        compileOnly("it.unimi.dsi:fastutil:8.5.15")
        implementation("com.google.guava:guava:33.3.1-jre")
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
    paperweight.paperDevBundle("1.21.1-R0.1-SNAPSHOT")
    compileOnly("net.kyori:adventure-api:4.17.0")
    compileOnly("it.unimi.dsi:fastutil:8.5.15")
    compileOnly("com.mojang:authlib:3.13.56")
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            pom {
                groupId = "de.verdox.mccreativelab"
                artifactId = "mcc-wrapper"
                version = "1.0.0-SNAPSHOT"
                from(components["java"])
                licenses {
                    license {
                        name = "GNU GENERAL PUBLIC LICENSE Version 3"
                        url = "https://www.gnu.org/licenses/gpl-3.0.en.html"
                    }
                }
                developers {
                    developer {
                        id = "verdox"
                        name = "Lukas Jonsson"
                        email = "mail.ysp@web.de"
                    }
                }
            }
        }
    }
}
