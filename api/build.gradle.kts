val enabled_platforms: String by rootProject
architectury {
    common(enabled_platforms.split(","))
}

val fabric_loader_version: String by rootProject

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${fabric_loader_version}")
}


