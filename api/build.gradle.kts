val enabled_platforms: String by rootProject
architectury {
    common(enabled_platforms.split(","))
}

val fabric_loader_version: String by rootProject

dependencies {
}

tasks.remapJar {
    archiveClassifier.set("")
}
tasks.jar {
    archiveClassifier.set("dev")
}

