plugins {
    id("java")
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "1.7.3"
    id("xyz.jpenilla.run-paper") version "2.3.1" // Adds runServer and runMojangMappedServer tasks for testing
    id("com.github.johnrengelman.shadow") version "8.1.1" apply true
}

sourceSets {
    main {
        java {
            srcDir("generated")
        }
    }
}

tasks.build {
    dependsOn(project(":api"))
}

tasks.register("runCodeGenerator") {
    group = "CodeGen"
    description = "First updates the wrapper files in the code gen then runs the code gen."

    // Task aus otherModule ausführen
    dependsOn(project(":mcc-wrapper").tasks.named("publishToMavenLocal"))

    // Lokalen Task ausführen
    dependsOn(tasks.named("runServer"))

    doLast {
        println("Tasks from otherModule and localProject executed!")
    }
}

tasks.register("runCodeGeneratorNoUpdate") {
    group = "CodeGen"
    description = "Runs the code generator without updating the cached mcc wrapper files."

    // Lokalen Task ausführen
    dependsOn(tasks.named("runServer"))

    doLast {
        println("Tasks from otherModule and localProject executed!")
    }
}

dependencies {
    paperweight.paperDevBundle(providers.gradleProperty("version").get())


    compileOnly("net.kyori:adventure-api:4.17.0")
    //implementation(project(":mcc-wrapper"))
    implementation(
        providers.gradleProperty("wrapper_group").get() + ":api:" + providers.gradleProperty("version").get()
    )
    implementation(
        providers.gradleProperty("wrapper_group").get() + ":vanilla:" + providers.gradleProperty("version").get()
    )
    implementation(
        providers.gradleProperty("wrapper_group").get() + ":paper:" + providers.gradleProperty("version").get()
    )
    implementation("com.google.guava:guava:33.3.1-jre")
    implementation("org.reflections:reflections:0.10.2")
    implementation("net.bytebuddy:byte-buddy:1.15.10")
    implementation("com.github.javaparser:javaparser-core:3.25.4")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains:annotations:26.0.1")
    testImplementation("org.ow2.asm:asm-tree:9.7")
    testImplementation("com.google.guava:guava:33.3.1-jre")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            pom {
                groupId = "de.verdox.mccreativelab"
                artifactId = "mcc-class-generator"
                version = providers.gradleProperty("version").get()
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

tasks.test {
    useJUnitPlatform()
}