package moe.nea.jarvis.fabric;

import moe.nea.jarvis.api.JarvisPlugin;
import moe.nea.jarvis.impl.JarvisContainer;
import moe.nea.jarvis.impl.JarvisUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

public class Main implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        List<JarvisPlugin> jarvisPlugins = FabricLoader.getInstance().getEntrypoints("jarvis", JarvisPlugin.class);
        JarvisContainer container = JarvisContainer.init();
        container.plugins.addAll(jarvisPlugins);
        if (JarvisUtil.isTest)
            container.plugins.add(new TestPluginClass());
        container.finishLoading();
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            container.registerCommands(dispatcher);
        });
    }
}
