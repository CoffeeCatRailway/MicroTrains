package coffeecatteam.microtrains.objects.items;

import coffeecatteam.microtrains.Reference;
import coffeecatteam.microtrains.Tabs;
import coffeecatteam.microtrains.objects.entity.EntityLocomotive;
import coffeecatteam.microtrains.util.iinterface.IOreDict;
import coffeecatteam.microtrains.util.iinterface.SubModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemLocomotive extends Item implements SubModels, IOreDict {

    private String oreDict;

    public ItemLocomotive(String name, String oreDict) {
        this.oreDict = oreDict;

        setUnlocalizedName(name);
        setRegistryName(name);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        setCreativeTab(Tabs.MICROTAB_LOCOS);
    }

    @Override
    public String getOreDict() {
        return this.oreDict;
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

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (GuiScreen.isShiftKeyDown()) {
            String info = I18n.format("item." + this.getOreDict() + ".info");
            tooltip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(info, 150));
        } else {
            tooltip.add(TextFormatting.YELLOW + I18n.format("item.show_info", "SHIFT"));
        }
    }
}
