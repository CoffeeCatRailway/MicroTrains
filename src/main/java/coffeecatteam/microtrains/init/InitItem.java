package coffeecatteam.microtrains.init;

import coffeecatteam.microtrains.objects.items.ItemBase;
import coffeecatteam.microtrains.objects.items.ItemLocomotive;
import coffeecatteam.microtrains.objects.items.ItemRollingStock;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;

public class InitItem {

    /*
     * Locomotives
     */
    public static final Item[] LOCO_STEAMS = new Item[EnumDyeColor.values().length];
    public static final Item[] LOCO_DIESELS = new Item[EnumDyeColor.values().length];
    static {
        for(int i = 0; i < EnumDyeColor.values().length; i++) {
            String color = "_" + EnumDyeColor.byMetadata(i).getName();
            LOCO_STEAMS[i] = new ItemLocomotive("loco_steam" + color, "loco_steam");
            LOCO_DIESELS[i] = new ItemLocomotive("loco_diesel" + color, "loco_diesel");
        }
    }

    /*
     * Rolling Stock
     */
    public static final Item STOCK_CABOOSE = new ItemRollingStock("caboose");
    public static final Item STOCK_COACH = new ItemRollingStock("coach");

    /*
     * Other
     */
    public static final Item MICRO_RAIL = new ItemBase("micro_rail");

	public static void init() {
        /*
         * Locomotives
         */
        register(LOCO_STEAMS);
        register(LOCO_DIESELS);

        /*
         * Rolling Stock
         */
        register(STOCK_CABOOSE);
        register(STOCK_COACH);

        /*
         * Other
         */
        register(MICRO_RAIL);
	}

    private static void register(Item... items) {
        for (Item item : items)
            register(item);
    }

	private static void register(Item item) {
        RegistrationHandler.Items.ITEMS.add(item);
    }
}
