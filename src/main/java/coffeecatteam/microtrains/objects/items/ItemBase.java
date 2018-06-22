package coffeecatteam.microtrains.objects.items;

import coffeecatteam.microtrains.MicroTrains;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(MicroTrains.MICROTAB);
    }
}
