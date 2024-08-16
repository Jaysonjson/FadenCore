Library/Core Mod for Minecraft Fabric focused on adding RPG Elements

# Readme is wip and just fast written

clone this Repo and run the gradle task "publishToMavenLocal"


or use the following in your build.gradle file:

```gradle
	maven {
		name = "Jaysonjson"
		url = "https://maven.pkg.github.com/Jaysonjson/FadenCore"
	}
```

this will require Github Authentication to work, you can create a Personal Access Token in your Github Account Settings and use it as the password in the gradle.properties file


then you can implement it using this
    
```gradle
dependencies {
	modImplementation "json.jayson:faden-core:${project.faden_core_version}"
}
```
