plugins {
    id("java")
    id("java-library")
    id("io.papermc.paperweight.userdev")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

sourceSets {
    main {
        java.srcDir("./vanilla/mod/src")
    }
}

dependencies {
    api(project(":vanilla", "default"))
    compileOnly(project(":paper"))

    paperweight.paperDevBundle(providers.gradleProperty("version").get())

    compileOnly("space.vectrix.ignite:ignite-api:1.1.0")
    compileOnly("net.fabricmc:sponge-mixin:0.15.2+mixin.0.8.7")
    compileOnly("io.github.llamalad7:mixinextras-common:0.4.1")
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveClassifier.set("") // ohne "-all" im Namen
    }

    build {
        dependsOn(shadowJar)
    }
}

sourceSets {
    main {
        java {
            srcDir("generated")
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            pom {
                groupId = providers.gradleProperty("wrapper_group").get()
                version = providers.gradleProperty("version").get()
                artifactId = "paper-mod"

                //artifact(tasks.named("shadowJar"))
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