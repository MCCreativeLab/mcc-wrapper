plugins {
    id("java")
    id("fabric-loom") version "1.9-SNAPSHOT"
}

repositories {
    maven("https://maven.parchmentmc.org")
}

dependencies {
    val mcVersion = providers.gradleProperty("mcversion").get()
    compileOnly(project(":api"))
    compileOnly("de.verdox.mccreativelab:mcc-pack-generator:" + providers.gradleProperty("pack_generator_version").get())

    minecraft("com.mojang:minecraft:$mcVersion")
    @Suppress("UnstableApiUsage")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-$mcVersion:2025.03.23@zip")
    })
    testImplementation("net.kyori:adventure-api:4.18.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation(project(":api"))
    testImplementation(project(":TestSuite"))
}

sourceSets {
    main {
        java {
            srcDir("generated")
        }
    }
}
tasks.test {
    useJUnitPlatform()
}

/*tasks.named<Jar>("reobfJar") {
    archiveClassifier.set("") // Entfernt den "dev"-Classifier
}*/

artifacts {
    add("default", tasks.jar)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            pom {
                groupId = providers.gradleProperty("wrapper_group").get()
                version = providers.gradleProperty("version").get() // Nur für die Veröffentlichung bereinigen
                artifactId = "vanilla"
                from(components["java"])


                artifact(tasks.named("jar")) {
                    classifier = "dev"
                }

                //artifact(tasks.named("reobfJar"))
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