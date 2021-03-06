package coffeecatteam.microtrains;

import coffeecatteam.microtrains.init.InitBlock;
import coffeecatteam.microtrains.init.InitItem;
import coffeecatteam.microtrains.proxy.ProxyCommon;
import coffeecatteam.microtrains.util.GuiHandler;
import coffeecatteam.microtrains.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class MicroTrains {

    public static Logger logger = Utils.getLogger();

    @Mod.Instance
    public static MicroTrains instance;

    @SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.COMMONPROXY)
    private static ProxyCommon proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        logger.info("Pre Initialize");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        logger.info("Initialize");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        logger.info("Post Initialize");
    }
}
