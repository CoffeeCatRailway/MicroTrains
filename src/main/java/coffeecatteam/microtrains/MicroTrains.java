package coffeecatteam.microtrains;

import coffeecatteam.microtrains.init.InitBlock;
import coffeecatteam.microtrains.proxy.ProxyCommon;
import coffeecatteam.microtrains.util.Utils;
import cofh.redstoneflux.RedstoneFlux;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = RedstoneFlux.VERSION_GROUP)
public class MicroTrains {

    public static final CreativeTabs MICROTAB = new TabMicroTrains("microtab");

    public static Logger logger = Utils.getLogger();

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
        logger.info("Initialize");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        logger.info("Post Initialize");
    }

    private static class TabMicroTrains extends CreativeTabs {

        public TabMicroTrains(String label) {
            super(label);
            this.setBackgroundImageName("micro.png");
            this.setNoTitle();
        }

        public ItemStack getTabIconItem() {
            return new ItemStack(InitBlock.TABLE);
        }
    }
}
