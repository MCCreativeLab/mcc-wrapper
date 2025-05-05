plugins {
    `java-library`
    id("java")
    id("io.papermc.paperweight.userdev")
    id("xyz.jpenilla.run-paper") version "2.3.1" apply true // Adds runServer and runMojangMappedServer tasks for testing
    id("com.github.johnrengelman.shadow") version "8.1.1" apply true
}

dependencies {
    //compileOnly(project(":api"))
    api(project(":vanilla", "default"))
    //compileOnly("io.projectreactor:reactor-core:3.7.5")
    //compileOnly("de.verdox.mccreativelab:mcc-pack-generator:" + providers.gradleProperty("pack_generator_version").get())


    paperweight.paperDevBundle(providers.gradleProperty("version").get())

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation(project(":api"))
    testImplementation(project(":vanilla", "default"))
    testImplementation(project(":TestSuite"))
}

tasks.named("build") {
    dependsOn(":paper:shadowJar")  // Stellt sicher, dass das shadowJar von API gebaut wird, bevor der Haupt-Build läuft
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

tasks.test {
    useJUnitPlatform()
}

val writeVersionFile by tasks.registering(WriteVersionFile::class) {
    versionProp.set(providers.gradleProperty("version"))
}

// Sicherstellen, dass processResources darauf wartet
tasks.processResources {
    dependsOn(writeVersionFile)
}

abstract class WriteVersionFile : DefaultTask() {

    @get:Input
    abstract val versionProp: Property<String>

    @get:OutputFile
    val outputFile: RegularFileProperty = project.objects.fileProperty().convention(
        project.layout.projectDirectory.file("build/resources/main/mcc-version.txt")
    )

    @TaskAction
    fun write() {
        val version = versionProp.get()
        outputFile.get().asFile.apply {
            parentFile.mkdirs()
            writeText(version)
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            pom {
                groupId = providers.gradleProperty("wrapper_group").get()
                version = providers.gradleProperty("version").get()
                artifactId = "paper"

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