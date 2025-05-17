plugins {
    id("java")
    id("io.papermc.paperweight.userdev")
}

dependencies {
    compileOnly(project(":vanilla", "default"))
    compileOnly(project(":paper"))

    paperweight.paperDevBundle(providers.gradleProperty("version").get())

    compileOnly("space.vectrix.ignite:ignite-api:1.1.0")
    compileOnly("net.fabricmc:sponge-mixin:0.15.2+mixin.0.8.7")
    compileOnly("io.github.llamalad7:mixinextras-common:0.4.1")
}

sourceSets {
    main {
        java {
            srcDir("generated")
        }
    }
}