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
        maven("https://jitpack.io")
        maven { url = uri("https://www.jitpack.io" ) }

	}

}
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		mavenCentral()
        maven("https://jitpack.io")
        maven { url = uri("https://www.jitpack.io" ) }
	}
}

rootProject.name = "YandexSummerSchool"
include(":app")
include(":features")
include(":features:expenses")
include(":features:incomes")
include(":features:account")
include(":features:analysis")
include(":features:articles")
include(":features:myHistory")
include(":features:editTransactions")
include(":features:settings")
include(":core")
include(":core:data")
include(":core:domain")
include(":core:common")
include(":core:charts")
