// Settings file from https://github.com/PaperMC/paperweight-test-plugin/blob/master/settings.gradle.kts

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "PluginTemplate"
include("src")
