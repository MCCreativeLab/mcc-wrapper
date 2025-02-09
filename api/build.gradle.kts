plugins {
    id("java")
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-releases/")
}

dependencies {
    implementation("com.google.guava:guava:33.3.1-jre")
    //compileOnly("com.mojang:authlib:1.5.25")
    implementation("org.joml:joml:1.10.8")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation(project(":mcc-class-generator"))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("de.verdox:vserializer:+")
}

tasks.test {
    useJUnitPlatform()
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