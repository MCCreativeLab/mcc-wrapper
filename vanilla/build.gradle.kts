plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.7.3"
    id("xyz.jpenilla.run-paper") version "2.3.1" apply false // Adds runServer and runMojangMappedServer tasks for testing
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":api"))
    paperweight.paperDevBundle(providers.gradleProperty("version").get())

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation(project(":api"))
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            pom {
                groupId = providers.gradleProperty("wrapper_group").get()
                version = providers.gradleProperty("version").get() // Nur für die Veröffentlichung bereinigen
                artifactId = "vanilla"
                from(components["java"])

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