# Jarvis CCI

> Common Config Index - A search engine for your minecraft mod configs.

This README currently is more *sollen* instead of *sein*.

## For Users

Jarvis is a search engine for all your minecraft mods (that support Jarvis). That means you can search for config
options in one place and get a direct link that opens that mods config menu at the correct position. Furthermore, Jarvis
has a HUD editor that allows you to edit the HUDs of all mods (that support Jarvis).

### Installation

Just grab the latest forge or fabric version and put it in your mod folder. Jarvis will automatically detect mods that
support it.

To open Jarvis in game, type `/jarvis` or press the "Jarvis" button inside the pause menu.


## For Developers

To support Jarvis just depend on `moe.nea.jarvis:jarvis-api:<version>`.

Everything starts with a `JarvisPlugin` that you need to implement and register.

(Example for groovy, note that version might be different!)
```
repositories {
	maven { url "https://repo.nea.moe/releases/" }
}

dependencies {
    modImplementation("moe.nea.jarvis:jarvis-api:1.0.1")
	include("moe.nea.jarvis:jarvis-fabric:1.0.1")
	modLocalRuntime("moe.nea.jarvis:jarvis-fabric:1.0.1")
}
```

### Fabric

For fabric you just need to add a `jarvis` entrypoint in your `fabric.mod.json`, pointing to a **class** implementing
`moe.nea.jarvis.api.JarvisPlugin`.

```json
{
  "entrypoints": {
    "client": [
      "my.mod.ClientInit"
    ],
    "jarvis": [
      "my.mod.MyJarvisPlugin"
    ]
  }
}
```

### Forge

For Forge you register an IMC enqueue listener like so:

```java
@Mod("mymod")
public class MyMod {
    public MyMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onEnqueue);
    }

    public void onEnqueue(InterModEnqueueEvent event) {
        InterModComms.sendTo(JarvisConstants.MODID, JarvisConstants.IMC_REGISTER_PLUGIN, () -> MyJarvisPlugin.class);
    }
}
```

