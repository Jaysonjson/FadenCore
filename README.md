Library/Core Mod for Minecraft Fabric focused on adding RPG Elements

# Readme is wip and just fast written

# How to Install
## Using Local Maven Repository (Preferred as of now)
clone this Repo and run the gradle task "publishToMavenLocal"
inside your Mod's build.gradle file add the following:
```gradle
mavenLocal()
```

this must be inside the repositories block
```gradle
repositories {
    mavenLocal()
    ...
}
```

## Using Github Packages

use the following in your build.gradle file:
```gradle
maven {
	name = "Jaysonjson"
	url = "https://maven.pkg.github.com/Jaysonjson/FadenCore"
}
```
this will require Github Authentication to work, you can create a Personal Access Token in your Github Account Settings and use it as the password in the gradle.properties file (Not inside the Project but ~/.gradle/gradle.properties) or System Env.


then you can implement it using this inside the dependencies block:
```gradle
dependencies {
    ...
	modImplementation "json.jayson:faden-core:VERSION"
    ...
}
```
make sure to change the version to the one you want to use


# Further Questions/Wiki

The Wiki is located in https://github.com/Jaysonjson/FadenCore/wiki

You can ask any further questions inside the support tab of the Fuchsia Discord Server
https://discord.gg/rRyQaVS3SC
or look at FadenServer: https://github.com/FuchsiaTeam/FadenServer \
Feel free to ping Jayson.json inside the Discord