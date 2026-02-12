pluginManagement {
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

rootProject.name = "NapTrackerPublicSample"
include(":app")

// Optional (local dev): when this sample lives inside the SDK repo, you can build against
// the local `:naptracker` module instead of downloading from Maven Central.
//
// Usage:
//   ./gradlew -PuseLocalSdk=true :app:assembleDebug
val useLocalSdk = providers.gradleProperty("useLocalSdk").orNull?.toBoolean() == true
val parentSettings = file("../settings.gradle.kts")
if (useLocalSdk && parentSettings.exists()) {
    includeBuild("..") {
        dependencySubstitution {
            substitute(module("io.github.nasmedia-tech:nap-tracker"))
                .using(project(":naptracker"))
        }
    }
}
