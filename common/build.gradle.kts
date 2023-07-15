val enabled_platforms: String by rootProject
architectury {
    common(enabled_platforms.split(","))
}

loom {
    accessWidenerPath.set(file("src/main/resources/jarvis.accesswidener"))
}

val fabric_loader_version: String by rootProject

val apiSourceSet = sourceSets.create("api")

configurations {
    named(apiSourceSet.compileClasspathConfigurationName) {
       // extendsFrom(named(sourceSets.main.get().compileClasspathConfigurationName).get())
    }
}

dependencies {
    api(apiSourceSet.output)
    modImplementation("net.fabricmc:fabric-loader:${fabric_loader_version}")
}


