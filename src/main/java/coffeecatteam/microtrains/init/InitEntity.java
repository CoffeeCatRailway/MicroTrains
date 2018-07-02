package coffeecatteam.microtrains.init;

import coffeecatteam.microtrains.MicroTrains;
import coffeecatteam.microtrains.Reference;
import coffeecatteam.microtrains.objects.entity.EntityLocomotive;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class InitEntity {

    public static void init() {
        registerEntity("locomotive", EntityLocomotive.class, 0, 80);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int trackingRange) {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id, MicroTrains.instance, trackingRange, 1, true);
    }

    private static void registerVehicle(String name, int  id, Class<? extends Entity> clazz) {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), clazz, name, id, MicroTrains.instance, 64, 1, true);
    }
}
