package coffeecatteam.microtrains.proxy;

import coffeecatteam.microtrains.objects.entity.EntityLocomotive;
import coffeecatteam.microtrains.objects.entity.RenderLocomotive;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ProxyClient extends ProxyCommon {

	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	public void init(FMLInitializationEvent event) {
		super.init(event);
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        RenderingRegistry.registerEntityRenderingHandler(EntityLocomotive.class, new RenderLocomotive(renderManager, Minecraft.getMinecraft().getRenderItem()));
	}

	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
}
