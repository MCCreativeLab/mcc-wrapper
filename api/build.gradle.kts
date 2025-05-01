plugins {
    id("java")
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1" apply true
}

repositories {
    maven("https://repo2.acrylicstyle.xyz/")
}

dependencies {
    api("de.verdox.mccreativelab:mcc-pack-generator:" + providers.gradleProperty("pack_generator_version").get())
    api("io.projectreactor:reactor-core:3.7.5")
    api("de.verdox:vserializer:1.2.3-SNAPSHOT")

    compileOnly("com.google.guava:guava:33.3.1-jre")
    compileOnly("net.kyori:adventure-text-serializer-gson:4.20.0")
    compileOnly("net.kyori:adventure-text-minimessage:4.20.0")
    compileOnly("com.mojang:authlib:3.13.56")
    compileOnly("org.joml:joml:1.10.8")
    compileOnly("org.apache.commons:commons-lang3:3.17.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

/*tasks.shadowJar {
    relocate("reactor.core", "de.verdox.shaded.reactor.core")
}*/

tasks.named("build") {
    dependsOn(":api:shadowJar")  // Stellt sicher, dass das shadowJar von API gebaut wird, bevor der Haupt-Build läuft
}

tasks.named<Jar>("jar") {
    enabled = true
}

artifacts {
    add("default", tasks.named("shadowJar"))
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
                artifactId = "api"

                artifact(tasks.named("shadowJar"))  // Verwende das geshadete Jar

                //from(components["java"])
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