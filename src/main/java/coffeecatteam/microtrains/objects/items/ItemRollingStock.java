package coffeecatteam.microtrains.objects.items;

import coffeecatteam.microtrains.Reference;
import coffeecatteam.microtrains.util.iinterface.SubModels;
import net.minecraft.util.ResourceLocation;

public class ItemRollingStock extends ItemBase implements SubModels {

    public ItemRollingStock(String name) {
        super(name);
    }

    @Override
    public ResourceLocation getModel() {
        ResourceLocation location = new ResourceLocation(Reference.MODID, "rolling_stock/" + this.getUnlocalizedName().substring(5));
        return location;
    }
}
