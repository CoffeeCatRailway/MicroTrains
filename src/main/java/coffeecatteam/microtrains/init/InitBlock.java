package coffeecatteam.microtrains.init;

import coffeecatteam.microtrains.MicroTrains;
import coffeecatteam.microtrains.objects.blocks.BlockTable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class InitBlock {

    public static final Block TABLE = new BlockTable("table", 1.0F, 1.5F, Material.ROCK, MicroTrains.MICROTAB);

	public static void init() {
        register(TABLE);
	}

    private static void register(Block... blocks) {
        for (Block block : blocks)
            register(block);
    }

    private static void register(Block block) {
	    RegistrationHandler.Blocks.BLOCKS.add(block);
        ItemBlock itemBlock = (ItemBlock) new ItemBlock(block).setRegistryName(block.getRegistryName());
        RegistrationHandler.Items.ITEMS.add(itemBlock);
    }

	public static void registerItemBlock(Block block, ItemBlock itemblock) {
		ForgeRegistries.BLOCKS.register(block);
		itemblock.setRegistryName(block.getRegistryName());
		ForgeRegistries.ITEMS.register(itemblock);
		
		if (itemblock instanceof ItemSlab)
			RegistrationHandler.Items.ITEMS.add(itemblock);
	}
}
