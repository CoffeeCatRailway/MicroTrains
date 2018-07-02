package coffeecatteam.microtrains;

import coffeecatteam.microtrains.init.InitBlock;
import coffeecatteam.microtrains.init.InitItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class Tabs {

    public static final CreativeTabs MICROTAB_BLOCKS = new TabBlocks("microtab_blocks");
    public static final CreativeTabs MICROTAB_LOCOS = new TabLocomotive("microtab_locos");
    public static final CreativeTabs MICROTAB_ROLLING_STOCK = new TabRollingStock("microtab_rolling_stock");

    private static class TabBlocks extends CreativeTabs {

        public TabBlocks(String label) {
            super(label);
            this.setBackgroundImageName("micro.png");
            this.setNoTitle();
        }

        public ItemStack getTabIconItem() {
            return new ItemStack(InitBlock.TABLE);
        }
    }

    private static class TabLocomotive extends CreativeTabs {

        public TabLocomotive(String label) {
            super(label);
            this.setBackgroundImageName("micro.png");
            this.setNoTitle();
        }

        public ItemStack getTabIconItem() {
            Item item;

            if (new Random().nextInt(1) == 0) {
                item = InitItem.LOCO_STEAMS[new Random().nextInt(InitItem.LOCO_STEAMS.length)];
            } else {
                item = InitItem.LOCO_DIESELS[new Random().nextInt(InitItem.LOCO_DIESELS.length)];
            }

            return new ItemStack(item);
        }
    }

    private static class TabRollingStock extends CreativeTabs {

        public TabRollingStock(String label) {
            super(label);
            this.setBackgroundImageName("micro.png");
            this.setNoTitle();
        }

        public ItemStack getTabIconItem() {
            return new ItemStack(InitItem.STOCK_COACH);
        }
    }
}
