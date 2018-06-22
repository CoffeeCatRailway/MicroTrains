package coffeecatteam.microtrains.gui.container;

import coffeecatteam.microtrains.objects.tileentity.TileCoalGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;

public class ContainerCoalGenerator extends Container {

    private final TileCoalGenerator tileentity;

    public ContainerCoalGenerator(IInventory player, TileCoalGenerator tileentity) {
        this.tileentity = tileentity;
        addSlotToContainer(new SlotFurnaceFuel(tileentity, 0, 52, 45));

        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

        for (int k = 0; k < 9; ++k)
            this.addSlotToContainer(new Slot(player, k, 8 + k * 18, 142));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack prev = ItemStack.EMPTY;
        Slot slot = (Slot) inventorySlots.get(index);
        int size = tileentity.getSizeInventory();
        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            prev = current.copy();
            if (index < size)
                if (!mergeItemStack(current, size, size + 36, true))
                    return ItemStack.EMPTY;
            else
                if (!mergeItemStack(current, 0, size, false))
                    return ItemStack.EMPTY;
            if (current.getCount() == 0)
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
            if (current.getCount() == prev.getCount())
                return ItemStack.EMPTY;
            slot.onTake(player, current);
        }
        return prev;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
