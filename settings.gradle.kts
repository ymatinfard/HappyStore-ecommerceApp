pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "HappyStore"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":feature:products")
include(":core:data")
include(":core:network")
include(":core:model")
include(":core:common")
include(":core:domain")
include(":core:designsystem")
include(":core:ui")
include(":core:testing")
include(":feature:cart")
include(":feature:profile")
include(":core:database")
include(":sync:work")
