package moe.nea.jarvis.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import moe.nea.jarvis.api.Jarvis;
import moe.nea.jarvis.api.JarvisHud;
import moe.nea.jarvis.api.JarvisPlugin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JarvisContainer extends Jarvis {
    public List<JarvisPlugin> plugins = new ArrayList<>();

    public static JarvisContainer init() {
        return new JarvisContainer();
    }


    @Override
    public Stream<JarvisPlugin> getAllPlugins() {
        return plugins.stream();
    }

    @Override
    public Stream<JarvisHud> getAllHuds() {
        return plugins.stream().flatMap(it -> it.getAllHuds().stream());
    }

    @Override
    public JarvisHudEditor getHudEditor(Screen lastScreen) {
        return getHudEditor(lastScreen, getAllHuds());
    }

    @Override
    public JarvisHudEditor getHudEditor(Screen lastScreen, List<JarvisHud> hudList) {
        return new JarvisHudEditor(lastScreen, hudList);
    }

    @Override
    public JarvisHudEditor getHudEditor(Screen lastScreen, BiPredicate<JarvisPlugin, JarvisHud> hudFilter) {
        return getHudEditor(lastScreen, getAllPlugins().flatMap(plugin -> plugin.getAllHuds().stream().filter(it -> hudFilter.test(plugin, it))));
    }

    private JarvisHudEditor getHudEditor(Screen lastScreen, Stream<JarvisHud> rStream) {
        return getHudEditor(lastScreen, rStream.collect(Collectors.toList()));
    }

    public void finishLoading() {
        plugins.forEach(it -> it.onInitialize(this));
    }

    public <S> void registerCommands(CommandDispatcher<S> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<S>literal("jarvis")
                .executes(context -> {
                    MinecraftClient.getInstance().send(() -> MinecraftClient.getInstance().setScreen(this.getHudEditor(null)));
                    return 0;
                }));
    }
}
