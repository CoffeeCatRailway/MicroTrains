package coffeecatteam.microtrains.init;

import coffeecatteam.microtrains.objects.items.ItemBase;
import net.minecraft.item.Item;

public class InitItem {

    /*
     * Locomotives
     */
    public static final Item LOCO_STEAM = new ItemBase("loco_steam");
    public static final Item LOCO_DIESEL = new ItemBase("loco_diesel");

    /*
     * Rolling Stock
     */
    public static final Item STOCK_CABOOSE = new ItemBase("stock_caboose");
    public static final Item STOCK_COACH = new ItemBase("stock_coach");

	public static void init() {
        /*
         * Locomotives
         */
        register(LOCO_STEAM, LOCO_DIESEL);

        /*
         * Rolling Stock
         */
        register(STOCK_CABOOSE);
        register(STOCK_COACH);
	}

    private static void register(Item... items) {
        for (Item item : items)
            register(item);
    }

	private static void register(Item item) {
        RegistrationHandler.Items.ITEMS.add(item);
    }
}
