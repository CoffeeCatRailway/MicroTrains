package coffeecatteam.microtrains.init;

import coffeecatteam.microtrains.Reference;
import coffeecatteam.microtrains.objects.blocks.BlockCoalGenerator;
import coffeecatteam.microtrains.objects.blocks.BlockController;
import coffeecatteam.microtrains.objects.blocks.BlockTable;
import coffeecatteam.microtrains.objects.tileentity.TileCoalGenerator;
import coffeecatteam.microtrains.objects.tileentity.TileController;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitBlock {

    public static final Block TABLE = new BlockTable("table");
    public static final Block CONTROLLER = new BlockController("controller");

    public static final Block COAL_GENERATOR = new BlockCoalGenerator("coal_generator");

	public static void init() {
        register(TABLE);
        register(CONTROLLER);

        register(COAL_GENERATOR);
	}

	public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileCoalGenerator.class, Reference.MODID+":coal_generator");
        GameRegistry.registerTileEntity(TileController.class, Reference.MODID+":controller");
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
