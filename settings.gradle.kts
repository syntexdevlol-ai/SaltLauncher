pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
rootProject.name = "Salt Launcher"
include(":FCL")
include(":FCLCore")
include(":FCLauncher")
include(":FCLLibrary")
include(":LWJGL-Pojav")
include(":LWJGL-Boat")
include(":Terracotta")
include(":ZipFileSystem")
//include(":NG-GL4ES")
