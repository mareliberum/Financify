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
include(":core:chart")
