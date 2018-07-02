package coffeecatteam.microtrains.objects.items;

import coffeecatteam.microtrains.Reference;
import coffeecatteam.microtrains.Tabs;
import coffeecatteam.microtrains.objects.entity.EntityLocomotive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemLocomotive extends ItemBase implements SubModels {

    public ItemLocomotive(String name) {
        super(name);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        setCreativeTab(Tabs.MICROTAB_LOCOS);
    }

    @Override
    public ResourceLocation getModel() {
        String[] parts = this.getUnlocalizedName().substring(5).split("_");
        String path = parts[0] + "_" + parts[1] + "/" + parts[2];
        if (parts.length > 3)
            path += "_" + parts[3];
        ResourceLocation location = new ResourceLocation(Reference.MODID, path);
        return location;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityPlayer, EnumHand hand) {
        ItemStack parItemStack = entityPlayer.getHeldItem(hand);

        if (!entityPlayer.capabilities.isCreativeMode) {
            parItemStack.shrink(1);
        }

        if (!world.isRemote)  {
            EntityLocomotive locomotive = new EntityLocomotive(world, entityPlayer);
            locomotive.setLoco(this);
            locomotive.shoot(entityPlayer, entityPlayer.rotationPitch, entityPlayer.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.spawnEntity(locomotive);
        }

        return super.onItemRightClick(world, entityPlayer, hand);
    }
}
