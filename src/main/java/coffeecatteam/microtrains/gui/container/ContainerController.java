package coffeecatteam.microtrains.gui.container;

import coffeecatteam.microtrains.gui.slot.SlotControllerOutput;
import coffeecatteam.microtrains.objects.tileentity.TileController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerController extends Container {

    private final TileController controller;

    public ContainerController(IInventory player, TileController controller) {
        this.controller = controller;
        addSlotToContainer(new SlotControllerOutput(controller, 0, 8, 67));

        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 98 + i * 18));

        for (int k = 0; k < 9; ++k)
            this.addSlotToContainer(new Slot(player, k, 8 + k * 18, 156));
    }

    private boolean performMerge(int slotIndex, ItemStack stack) {
        int size = controller.getSizeInventory();

        if (slotIndex < size) {
            return mergeItemStack(stack, size, size + 26, true);
        }
        return mergeItemStack(stack, 0, size, false);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();

            if (!performMerge(index, stackInSlot)) {
                return ItemStack.EMPTY;
            }
            slot.onSlotChange(stackInSlot, stack);

            if (stackInSlot.getCount() <= 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.putStack(stackInSlot);
            }
            if (stackInSlot.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, stackInSlot);
        }
        return stack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
