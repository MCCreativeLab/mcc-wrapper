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
    api(project(":paper"))

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