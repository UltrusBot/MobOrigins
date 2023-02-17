plugins {
    id("fabric-loom") version "0.11.+"
    id("maven-publish")
    id("io.github.juuxel.loom-quiltflower") version "1.6.0"
//    id("com.github.breadmoirai.github-release") version "2.4.1"
//    id("com.modrinth.minotaur") version "2.+"
//    id("com.matthewprenger.cursegradle") version "1.4.0"
}

val archives_base_name: String by project
val mod_version: String by project
val maven_group: String by project

apply {
    from("https://raw.githubusercontent.com/UltrusBot/GradleScripts/master/release.gradle.kts")
}
group = maven_group
version = mod_version
base.archivesName.set(archives_base_name)
tasks {
    processResources {
        inputs.property("version", version)

        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to version))
        }
        val environment = System.getenv("ENVIRONMENT") ?: "production"
        println("Environment: $environment")
        if (environment != "production") {
            from(rootProject.rootDir.absolutePath + "/testdata") { into("data") }
        }
    }

    jar {
        from("LICENSE") {
            rename { "LICENSE_${archives_base_name}" }
        }
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
}


repositories {
    maven {
        name = "Quilt"
        url = uri("https://maven.quiltmc.org/repository/release")
    }
    maven {
        name = "Quilt Snapshots"
        url = uri("https://maven.quiltmc.org/repository/snapshot")
    }
    maven(url = "https://api.modrinth.com/maven")
    maven(url = "https://maven.terraformersmc.com/")
    maven(url = "https://jitpack.io")
    maven(url = "https://maven.jamieswhiteshirt.com/libs-release/")
    maven(url = "https://maven.shedaniel.me/")
    maven(url = "https://maven.terraformersmc.com/")
    maven(url = "https://ladysnake.jfrog.io/artifactory/mods")
}

java {
    withSourcesJar()
}
dependencies {
    minecraft(libs.minecraft)
    mappings(variantOf(libs.quilt.mappings) { classifier("intermediary-v2") })
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modImplementation(libs.modmenu)
    modImplementation(libs.origins)

    implementation(libs.mixinextras)
    annotationProcessor(libs.mixinextras)
    include(libs.mixinextras)
}


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}