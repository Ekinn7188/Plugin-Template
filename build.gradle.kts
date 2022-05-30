plugins {
    `java-library`

    // Minecraft
    id("io.papermc.paperweight.userdev") version "1.3.6"
    id("xyz.jpenilla.run-paper") version "1.0.6"

    // Fat Jar
    id("com.github.johnrengelman.shadow") version "7.0.0"

    // Database
    id("nu.studer.jooq") version "6.0.1"
    id("org.flywaydb.flyway") version "8.0.5"
}

group = "Jeeper"
version = "1.0"
description = "Plugin Template"
val pluginName = "PluginTemplate"
val dbName = "PluginTemplate.db"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")

    implementation("org.reflections:reflections:0.10.2")

    implementation("jeeper.utils:PaperPluginUtils:1.2")

    compileOnly("net.kyori:adventure-text-minimessage:4.10.1")

    //database
    implementation("org.jooq:jooq:3.16.6")
    compileOnly("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("org.flywaydb:flyway-core:8.5.10")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    jooqGenerator("org.xerial:sqlite-jdbc:3.36.0.3")
}

flyway {
    url = "jdbc:sqlite:${buildDir}/generate-source.db"
}

jooq {
    configurations {
        create("main") {
            jooqConfiguration.apply{
                jdbc.apply {
                    driver = "org.sqlite.JDBC"
                    url = "jdbc:sqlite:${buildDir}/generate-source.db"
                }
                generator.apply {
                    database.apply {
                        name = "org.jooq.meta.sqlite.SQLiteDatabase"
                        excludes = "flyway_schema_history|sqlite_master|sqlite_sequence"
                    }
                    target.apply {
                        packageName = dbName
                    }
                }
            }
        }
    }
}

val cleanGenerateSourceDB by tasks.registering {
    delete(file("${buildDir}/generate-source.db"))
}

val cleanServer by tasks.registering {

    delete(file("$projectDir/$dbName"))
    delete(file("${projectDir}/run/usercache.json"))
    delete(files("${projectDir}/run/plugins/$pluginName"))
    delete(files("${projectDir}/run/world/playerdata"))

}

val emptyConfig by tasks.registering {
    delete(
        fileTree(file("${projectDir}/run/plugins/$pluginName")) {
            include("**.yml")
        }
    )
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
    runServer {
        minecraftVersion("1.18.2")
    }

    flywayMigrate {
        dependsOn(cleanGenerateSourceDB)
    }

    named<nu.studer.gradle.jooq.JooqGenerate>("generateJooq") {
        dependsOn(flywayMigrate)
    }

    //classes depends on generateJooq
    classes {
        dependsOn(named<nu.studer.gradle.jooq.JooqGenerate>("generateJooq"))
    }




}