package moe.nea.jarvis.forge;

import moe.nea.jarvis.api.JarvisConstants;
import moe.nea.jarvis.api.JarvisPlugin;
import moe.nea.jarvis.impl.JarvisContainer;
import moe.nea.jarvis.impl.JarvisUtil;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@Mod(JarvisConstants.MODID)
public class Main {
    JarvisContainer jarvisContainer = JarvisContainer.init();

    public Main() {
        if (FMLEnvironment.dist.isClient()) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onDequeue);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onEnqueue);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onComplete);
            MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);
        }
    }

    public void onEnqueue(InterModEnqueueEvent event) {
        if (JarvisUtil.isTest)
            InterModComms.sendTo(JarvisConstants.MODID, JarvisConstants.IMC_REGISTER_PLUGIN, () -> TestPluginClass.class);
    }

    public void onRegisterCommands(RegisterClientCommandsEvent event) {
        jarvisContainer.registerCommands(event.getDispatcher());
    }

    public void onComplete(FMLLoadCompleteEvent event) {
        jarvisContainer.finishLoading();
    }

    public void onDequeue(InterModProcessEvent event) {
        InterModComms.getMessages(JarvisConstants.MODID).forEach(mes -> {
            if (!Objects.equals(JarvisConstants.IMC_REGISTER_PLUGIN, mes.method())) {
                throw new IllegalArgumentException("Invalid message to jarvis: " + mes + ". Unknown method name." + " Contact the authors of " + mes.senderModId() + " before contacting Jarvis.");
            }
            Object argument = mes.messageSupplier().get();
            if (!(argument instanceof Class<?> clazz)) {
                throw new IllegalArgumentException("Invalid message to jarvis: " + mes + ". " + argument + " should be a class" + " Contact the authors of " + mes.senderModId() + " before contacting Jarvis.");
            }
            if (!JarvisPlugin.class.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException("Invalid message to jarvis: " + mes + ". " + argument + " should be subclass of JarvisPlugin" + " Contact the authors of " + mes.senderModId() + " before contacting Jarvis.");
            }

            JarvisPlugin jarvisPlugin;
            try {
                Constructor<?> declaredConstructor = clazz.getConstructor();
                jarvisPlugin = (JarvisPlugin) declaredConstructor.newInstance();
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException("Invalid message to jarvis: " + mes + ". No public default constructor." + " Contact the authors of " + mes.senderModId() + " before contacting Jarvis.");
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new IllegalArgumentException("Invalid message to jarvis: " + mes + ". Uncaught exception during constructor invocation." + " Contact the authors of " + mes.senderModId() + " before contacting Jarvis.", e);
            }
            jarvisContainer.plugins.add(jarvisPlugin);
        });
    }

}
